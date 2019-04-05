package rz.com.catolico.retrofit

import com.google.gson.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import rz.com.catolico.interfaces.SantoService
import rz.com.catolico.interfaces.UsuarioService
import java.lang.reflect.Type
import java.util.*
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonElement




class RetrofitConfig {

    private val BASE_URL: String = "http://52.67.31.101:8080/"

    var builder = GsonBuilder().registerTypeAdapter(Date::class.java, object : JsonDeserializer<Date> {
        @Throws(JsonParseException::class)

        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Date {
            return Date(json.asJsonPrimitive.asLong)
        }
    })



// Register an adapter to manage the date types as long values

    /*builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
        public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return new Date(json.getAsJsonPrimitive().getAsLong());
        }
    });*/

    private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(builder.create()))
            .build()


    fun usuarioService(): UsuarioService {
        return retrofit.create(UsuarioService::class.java)
    }

    fun getSantoService(): SantoService {
        return retrofit.create(SantoService::class.java)
    }

}