package rz.com.catolico.interfaces

import com.orhanobut.hawk.Hawk
import rz.com.catolico.bean.Santo
import rz.com.catolico.bean.Usuario
import rz.com.catolico.utils.Constantes

interface IFavoriteSanto : IFavorite<Santo> {

    override fun onUpdateFavorite(type: Santo, user: Usuario) {
        if (type.favorite) user.removeSanto(type) else user.addSanto(type)
        onSaveFavorite(type, user)
    }

    override fun onErrorUpdateFavorite(type: Santo, user: Usuario) {
        if (type.favorite) user.addSanto(type) else user.removeSanto(type)
    }

    override fun onSucessUpdateFavorite(type: Santo, user: Usuario) {
        if (type.favorite) user.addSanto(type) else user.removeSanto(type)
        Hawk.put(Constantes.USER_KEY, user)
    }

}