package rz.com.catolico.bean

import java.io.Serializable

class Usuario : BaseEntityName(), Serializable,Cloneable {

    var idFacebook: String? = null
    var email: String? = null
    var password: String? = null
    var oldPassword: String? = null
    var newPassword: String? = null
    var tipoUsuario: TipoUsuario? = null
    var oracoes: MutableList<Oracao> = ArrayList()
    var uhs: MutableList<Santo> = ArrayList()

    fun addOracao(oracao: Oracao) {
        oracoes.add(oracao)
    }

    fun addSanto(santo: Santo) {
        uhs.add(santo)
    }

    fun removeSanto(santo: Santo){
        uhs.remove(santo)
    }

    fun removeOracao(oracao: Oracao){
        oracoes.remove(oracao)
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