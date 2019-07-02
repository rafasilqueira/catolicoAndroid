package rz.com.catolico.interfaces

import android.content.Context
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rz.com.catolico.bean.Usuario
import rz.com.catolico.callBack.CallBackDialog
import rz.com.catolico.retrofit.RetrofitConfig
import rz.com.catolico.utils.ToastMisc

interface ILogin {

    fun doLoginSucess(usuario: Usuario)

    fun doLoginError(context: Context) {
        ToastMisc.generalError(context)
    }

    fun doLogin(usuario: Usuario, context: Context , showDialog : Boolean) {
        val call: Call<Usuario> = if (usuario.idFacebook != null)
            RetrofitConfig().usuarioService().getUserFacebook(usuario)
        else
            RetrofitConfig().usuarioService().getUser(usuario)

        if(showDialog){
            callRequestWithDialog(context,call)
        }else{
            callRequestWithoutDialog(context,call)
        }
    }

    private fun callRequestWithDialog(context: Context, call: Call<Usuario>) {
        call.enqueue(object : CallBackDialog<Usuario>(context) {

            override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                super.onResponse(call, response)
                if (response.body() != null)
                    doLoginSucess(response.body()!!)
                else
                    ToastMisc.userNotFound(context)
                //println(response?.body())
            }

            override fun onFailure(call: Call<Usuario>, t: Throwable) {
                super.onFailure(call, t)
                t.printStackTrace()
                doLoginError(context)
            }

        })

    }

    private fun callRequestWithoutDialog(context: Context, call: Call<Usuario>) {
        call.enqueue(object : Callback<Usuario> {

            override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                if (response.body() != null)
                    doLoginSucess(response.body()!!)
                else
                    ToastMisc.userNotFound(context)
                //println(response?.body())
            }

            override fun onFailure(call: Call<Usuario>, t: Throwable) {
                t.printStackTrace()
                doLoginError(context)
            }

        })

    }

}