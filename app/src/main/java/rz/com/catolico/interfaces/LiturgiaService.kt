package rz.com.catolico.interfaces

import retrofit2.Call
import retrofit2.http.GET
import rz.com.catolico.bean.Liturgia
import rz.com.catolico.utils.Constantes.Companion.LITURGIA
import rz.com.catolico.utils.Constantes.Companion.TOP_LITURGIAS

interface LiturgiaService {

    @GET(LITURGIA + TOP_LITURGIAS)
    fun getTopLiturgias(): Call<MutableList<Liturgia>>

}

