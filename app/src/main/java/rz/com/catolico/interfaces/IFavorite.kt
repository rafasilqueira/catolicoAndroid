package rz.com.catolico.interfaces

import rz.com.catolico.bean.BaseEntityFavorite

interface IFavorite<T : BaseEntityFavorite> {

    fun syncronizeFavorites(sourceList: MutableList<T>, userList: MutableList<T>) {
        if (userList.isEmpty()) {
            return
        }

        sourceList.filter { sourceItem -> userList.any { it == sourceItem } }.forEach { it.favorite = true }
    }

}