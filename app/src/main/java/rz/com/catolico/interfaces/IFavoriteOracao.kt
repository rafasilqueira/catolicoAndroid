package rz.com.catolico.interfaces

import rz.com.catolico.bean.Oracao
import rz.com.catolico.bean.Usuario

interface IFavoriteOracao : IFavorite<Oracao> {

    override fun onUpdateFavorite(type: Oracao, user: Usuario) {
        if (type.favorite) user.removeOracao(type) else user.addOracao(type)
        onSaveFavorite(type, user)
    }

    override fun onErrorUpdateFavorite(type: Oracao, user: Usuario) {
        if (type.favorite) user.addOracao(type) else user.removeOracao(type)
    }

}