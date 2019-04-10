package rz.com.catolico.interfaces

import br.com.tupinamba.model.bean.Santo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import rz.com.catolico.utils.Constantes.Companion.CELEBRATION
import rz.com.catolico.utils.Constantes.Companion.LISTAR_TOP
import rz.com.catolico.utils.Constantes.Companion.SANTOS

interface SantoService {

    @GET(SANTOS + LISTAR_TOP)
    fun getLatests(): Call<List<Santo>>

    @GET(SANTOS + CELEBRATION+"/{celebration}")
    fun getByCelebrationDate(@Path("celebration")celebration : Long): Call<List<Santo>>

}