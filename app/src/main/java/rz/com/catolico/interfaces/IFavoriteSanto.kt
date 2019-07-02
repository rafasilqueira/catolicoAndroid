package rz.com.catolico.interfaces

import rz.com.catolico.bean.Santo
import rz.com.catolico.bean.Usuario

interface IFavoriteSanto : IFavorite<Santo> {

    override fun onUpdateFavorite(type: Santo, user: Usuario) {
        if (type.favorite) user.removeSanto(type) else user.addSanto(type)
        onSaveFavorite(type, user)
    }

    override fun onErrorUpdateFavorite(type: Santo, user: Usuario) {
        if (type.favorite) user.addSanto(type) else user.removeSanto(type)
    }

}