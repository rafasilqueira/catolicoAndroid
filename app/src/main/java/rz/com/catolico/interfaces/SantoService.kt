package rz.com.catolico.interfaces

import rz.com.catolico.bean.Santo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import rz.com.catolico.utils.Constantes.Companion.CELEBRATION
import rz.com.catolico.utils.Constantes.Companion.LISTAR_TOP
import rz.com.catolico.utils.Constantes.Companion.SANTOS

interface SantoService {

    @GET("$SANTOS$LISTAR_TOP")
    fun getLatests(): Call<MutableList<Santo>>

    @GET("$SANTOS$CELEBRATION/{celebration}")
    fun getByCelebrationDate(@Path("celebration")celebration : Long): Call<MutableList<Santo>>

}