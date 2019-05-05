package rz.com.catolico.interfaces

import retrofit2.Call
import retrofit2.http.GET
import rz.com.catolico.bean.Oracao
import rz.com.catolico.utils.Constantes.Companion.ORACOES

interface OracaoService {

    @GET(ORACOES)
    fun getOracoes(): Call<MutableList<Oracao>>

}