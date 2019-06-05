package rz.com.catolico.bean

import java.io.Serializable
import java.util.*

class Santo : BaseEntityFavorite(), Serializable {

     var comemoracao: Date? = null
     var imgurl: String? = null
     var santoDia: Boolean = false
     var diasData: Int? = null
     var oracoes: MutableList<Oracao> = ArrayList()

    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        } else if (o != null && this.javaClass == o.javaClass) {
            val santo = o as Santo?
            return this.id == santo!!.id
        } else {
            return false
        }
    }

    override fun hashCode(): Int {
        return Objects.hash(this.id?.let { arrayOf<Any>(it) })
    }

    fun addOracao(oracao:Oracao){
        oracoes.add(oracao)
    }

}