package rz.com.catolico.Dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import com.facebook.login.LoginManager
import com.orhanobut.hawk.Hawk
import retrofit2.Call
import retrofit2.Response
import rz.com.catolico.R
import rz.com.catolico.activiy.SplashScreenActivity
import rz.com.catolico.activiy.Settings
import rz.com.catolico.bean.CallBackDialog
import br.com.tupinamba.model.bean.Usuario
import rz.com.catolico.retrofit.RetrofitConfig
import rz.com.catolico.utils.Constantes.Companion.USER_KEY
import rz.com.catolico.utils.StatusFacebookLogin
import rz.com.catolico.utils.ToastMisc

class UserDeleteDialog : DialogFragment() {


    var usuario : Usuario? = null
    var mContext : Context? =null

    companion object {
        fun getInstance(usuario: Usuario): UserDeleteDialog {
            var userDeleteConfirmation = UserDeleteDialog()
            var bundle = Bundle()
            bundle.putSerializable(USER_KEY, usuario)
            userDeleteConfirmation.arguments = bundle
            return userDeleteConfirmation
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        this.mContext = context
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        usuario = arguments?.getSerializable(USER_KEY) as Usuario
        return AlertDialog.Builder(activity)
                .setTitle(getString(R.string.meus_dados))
                .setMessage(getString(R.string.delete_account_text))
                .setPositiveButton("YES") { dialog, which ->
                    deleteUser(usuario!!)
                    dialog.dismiss()
                }
                .setNegativeButton(android.R.string.no){dialog, which ->
                    dialog.dismiss()
                }
                .create()
    }

   private fun deleteUser(usuario: Usuario){
        val call: Call<Boolean> = RetrofitConfig().usuarioService().deleteUser(usuario)
        call.enqueue(object : CallBackDialog<Boolean>(mContext!!) {
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                super.onResponse(call, response)
                if(response?.body()!=null)
                   if(response?.body()!!){
                       ToastMisc.sucess(mContext!!)
                       endSession()
                   }
                else{ ToastMisc.generalError(mContext!!) }
            }
            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                super.onFailure(call, t)
                t.printStackTrace()
                ToastMisc.generalError(mContext!!)
            }
        })
    }


    private fun endSession() {
        if (StatusFacebookLogin.isFacebookLoggedIn(mContext!!)) {
            LoginManager.getInstance().logOut()
        }
        Hawk.init(mContext!!).build()
        Hawk.delete(USER_KEY)
        (mContext as Settings).startActivity(Intent((mContext as Settings), SplashScreenActivity::class.java)
                .putExtra("finish", true)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        )
        (mContext as Settings).finish()
    }
}