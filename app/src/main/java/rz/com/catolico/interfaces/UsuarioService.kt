package rz.com.catolico.interfaces

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import rz.com.catolico.bean.Usuario
import rz.com.catolico.utils.Constantes.Companion.DELETE
import rz.com.catolico.utils.Constantes.Companion.FORGOT_PASSWORD
import rz.com.catolico.utils.Constantes.Companion.LOGIN
import rz.com.catolico.utils.Constantes.Companion.LOGINFACEBOOK
import rz.com.catolico.utils.Constantes.Companion.SAVE_USER
import rz.com.catolico.utils.Constantes.Companion.USUARIO

interface UsuarioService {

    @POST(USUARIO + LOGIN)
    fun getUser(@Body usuario: Usuario): Call<Usuario>

    @POST(USUARIO + LOGINFACEBOOK)
    fun getUserFacebook(@Body usuario: Usuario): Call<Usuario>

    @POST(USUARIO + DELETE)
    fun deleteUser(@Body usuario: Usuario): Call<Boolean>

    @POST(USUARIO + FORGOT_PASSWORD)
    fun forgotPassword(@Body body: RequestBody): Call<Boolean>

    @POST(USUARIO + SAVE_USER)
    fun saveUser(@Body body: Usuario): Call<Boolean>

    @POST(USUARIO + SAVE_USER)
    fun changePassword(@Body body: Usuario): Call<Boolean>

}

