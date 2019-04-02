package rz.com.catolico.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import rz.com.catolico.interfaces.SantoService
import rz.com.catolico.interfaces.UsuarioService

class RetrofitConfig {

    private val BASE_URL: String = "http://52.67.31.101:8080/"

    private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    fun usuarioService(): UsuarioService {
        return retrofit.create(UsuarioService::class.java)
    }

    fun getSantoService(): SantoService {
        return retrofit.create(SantoService::class.java)
    }

}