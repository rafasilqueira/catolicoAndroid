package rz.com.catolico.bean

import java.io.Serializable

class Oracao : BaseEntityFavorite(), Serializable, Cloneable {
    var categoriaOracao: CategoriaOracao? = CategoriaOracao()

    public override fun clone(): Any {
        return try {
            super.clone()
        } catch (e: CloneNotSupportedException) {
            println("Cloning not allowed.")
            this
        }

    }
}