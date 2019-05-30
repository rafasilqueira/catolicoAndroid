package rz.com.catolico.retrofit

import com.google.gson.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import rz.com.catolico.interfaces.LiturgiaService
import rz.com.catolico.interfaces.OracaoService
import rz.com.catolico.interfaces.SantoService
import rz.com.catolico.interfaces.UsuarioService
import java.lang.reflect.Type
import java.util.*


class RetrofitConfig {

    private val BASE_URL: String = "http://52.67.31.101:8080/"
    //private val BASE_URL: String = "http://192.168.0.100:8080/"

    var builder = setupGsonBuilder()

  private  fun setupGsonBuilder(): GsonBuilder {

      return GsonBuilder().registerTypeAdapter(Date::class.java, object : JsonSerializer<Date> {
            override fun serialize(date: Date?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
                return JsonPrimitive(date?.time)
            }
        }).registerTypeAdapter(Date::class.java, object : JsonDeserializer<Date> {
            @Throws(JsonParseException::class)

            override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Date {
                return Date(json.asJsonPrimitive.asLong)
            }
        })

    }

    private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(builder.create()))
            .build()


    fun usuarioService(): UsuarioService {
        return retrofit.create(UsuarioService::class.java)
    }

    fun santoService(): SantoService {
        return retrofit.create(SantoService::class.java)
    }

    fun OracaoService(): OracaoService {
        return retrofit.create(OracaoService::class.java)
    }

    fun LiturgiaService(): OracaoService {
        return retrofit.create(LiturgiaService()::class.java)
    }

}