package rz.com.catolico.bean

import java.io.Serializable
import java.util.*

class Santo : BaseEntityFavorite(), Serializable, Cloneable {

     var comemoracao: Date? = null
     var imgurl: String? = null
     var santoDia = false
     var diasData: Int? = null
     var oracoes: MutableList<Oracao> = ArrayList()

    fun addOracao(oracao:Oracao){
        oracoes.add(oracao)
    }

    public override fun clone(): Any {
        return try {
            super.clone()
        } catch (e: CloneNotSupportedException) {
            println("Cloning not allowed.")
            this
        }

    }

}