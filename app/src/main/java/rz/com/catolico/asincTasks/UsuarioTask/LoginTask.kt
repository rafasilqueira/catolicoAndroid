package rz.com.catolico.asincTasks.UsuarioTask

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import retrofit2.Call
import retrofit2.Response
import rz.com.catolico.exception.CatolicoException
import rz.com.catolico.R
import br.com.tupinamba.model.bean.Usuario
import rz.com.catolico.interfaces.Usuario.Login
import rz.com.catolico.retrofit.RetrofitConfig
import rz.com.catolico.utils.ToastMisc

class LoginTask(private val context: Context, private val usuario: Usuario, private val showDialog: Boolean) : AsyncTask<Usuario, Void, Usuario>(){

    var TAG = "USUARIO TASK"
    var dialog: Dialog? = null
    var response: Response<Usuario>? = null
    var user: Usuario? = null


    override fun onPreExecute() {
        if (context != null && showDialog) {
            dialog = ProgressDialog.show(context, context.getString(R.string.aguarde), context.getString(R.string.dialog_wait_pt_br))

        }
    }

    @Throws(CatolicoException::class)
    override fun doInBackground(vararg params: Usuario?): Usuario? {
        val call: Call<Usuario> = if (usuario.idFacebook != null) {
            RetrofitConfig().usuarioService().getUserFacebook(usuario)
        } else {
            RetrofitConfig().usuarioService().getUser(usuario)
        }

        try {
            response = call.execute()
            return if(response?.body()!=null)response?.body()!! else null
        } catch (exception: Exception) {
            println(response?.errorBody().toString())
           /* if(

                    ){

            }*/
            exception.printStackTrace();
            //ToastMisc.generalError(context)
            throw CatolicoException(context.getString(R.string.general_error))
        }

    }

    @Throws(CatolicoException::class)
    override fun onPostExecute(result: Usuario?) {
        dialog?.dismiss()
        if (context is Login) {
            if (result != null) {
                (context as Login).doLoginSucess(result)
            } else {
                ToastMisc.userNotFound(context)
                return
            }
        } else {
            throw CatolicoException("A activity deve implementar interface Login")
        }

    }
}