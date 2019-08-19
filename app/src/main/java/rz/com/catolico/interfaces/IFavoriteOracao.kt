package rz.com.catolico.interfaces

import com.orhanobut.hawk.Hawk
import rz.com.catolico.bean.Oracao
import rz.com.catolico.bean.Usuario
import rz.com.catolico.utils.Constantes.Companion.USER_KEY

interface IFavoriteOracao : IFavorite<Oracao> {

    override fun onUpdateFavorite(type: Oracao, user: Usuario) {
        if (type.favorite) user.removeOracao(type) else user.addOracao(type)
        onSaveFavorite(type, user)
    }

    override fun onErrorUpdateFavorite(type: Oracao, user: Usuario) {
        if (type.favorite) user.addOracao(type) else user.removeOracao(type)
        Hawk.put(USER_KEY,user)
    }

}