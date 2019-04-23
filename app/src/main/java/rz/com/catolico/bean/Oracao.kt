package rz.com.catolico.bean

import java.io.Serializable

class Oracao : BaseEntityFavorite(), Serializable {
    var categoriaOracao: CategoriaOracao? = null
}