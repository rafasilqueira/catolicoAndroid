package rz.com.catolico.bean

import java.io.Serializable

class Usuario : BaseEntityName(), Serializable {

    var idFacebook: String? = null
    var email: String? = null
    var password: String? = null
    var oldPassword: String? = null
    var newPassword: String? = null
    var tipoUsuario: TipoUsuario? = null
    var oracoes: List<Oracao>? = null
    var uhs: MutableList<Oracao> = ArrayList()

    fun addOracao(oracao:Oracao){
        uhs?.add(oracao)
    }

}