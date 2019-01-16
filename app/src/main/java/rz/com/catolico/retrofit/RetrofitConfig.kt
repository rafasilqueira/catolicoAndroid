package rz.com.catolico.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import rz.com.catolico.interfaces.UsuarioService

class RetrofitConfig {

    var BASE_URL: String = "http://52.67.31.101:8080/"

    private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    fun usuarioService(): UsuarioService {
        return retrofit.create(UsuarioService::class.java)
    }

}