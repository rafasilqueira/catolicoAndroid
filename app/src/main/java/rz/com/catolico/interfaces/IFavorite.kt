package rz.com.catolico.interfaces

import retrofit2.Call
import rz.com.catolico.bean.BaseEntityFavorite
import rz.com.catolico.bean.Usuario
import rz.com.catolico.retrofit.RetrofitConfig

interface IFavorite<T : BaseEntityFavorite> {

    fun syncronizeFavorites(sourceList: MutableList<T>, userList: MutableList<T>) {
        sourceList?.filter { sourceItem -> userList.any { it == sourceItem } }.forEach { it.favorite = true }
    }

}