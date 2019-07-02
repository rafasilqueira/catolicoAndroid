package rz.com.catolico.interfaces

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rz.com.catolico.bean.BaseEntityFavorite
import rz.com.catolico.bean.Usuario
import rz.com.catolico.retrofit.RetrofitConfig

interface IFavorite<T : BaseEntityFavorite> {

    fun syncronizeFavorites(sourceList: MutableList<T>, userList: MutableList<T>) {
        if (userList.isEmpty()) {
            return
        }

        sourceList.filter { sourceItem -> userList.any { it == sourceItem } }.forEach { it.favorite = true }
    }

    fun onUpdateFavorite(type: T, user: Usuario)
    fun onSucessUpdateFavorite(type: T)
    fun onErrorUpdateFavorite(type: T, user: Usuario)

    fun onSaveFavorite(type: T, user: Usuario) {
        val call: Call<Boolean> = RetrofitConfig().usuarioService().saveUser(user)
        call.enqueue(object : Callback<Boolean> {
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if (response.isSuccessful) {
                    type.favorite = !type.favorite
                    onSucessUpdateFavorite(type)
                } else {
                    onErrorUpdateFavorite(type, user)
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                t.printStackTrace()
                onErrorUpdateFavorite(type, user)
            }
        })
    }

}