package rz.com.catolico.bean

import java.io.Serializable

class TipoUsuario() : Serializable {

    var id: Int = 0
    var descricao: String = ""
    var usuarios : List<Usuario> = emptyList()

    override fun toString(): String {
        return "TipoUsuario(id=$id, descricao='$descricao', usuarios=$usuarios)"
    }


}