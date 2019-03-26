package rz.com.catolico.bean

import java.io.Serializable

class Usuario() : Serializable{

    var id : Int = 0;
    var idFacebook : String? = null;
    var nome : String?  = null
    var email : String?  = null
    var password : String?  = null
    var newPassword : String?  = null
    var oldPassword : String?  = null
    var tipoUsuario : TipoUsuario = TipoUsuario()

    override fun toString(): String {
        return "Usuario(id=$id, idFacebook=$idFacebook, nome=$nome, email=$email, password=$password, newPassword=$newPassword, oldPassword=$oldPassword, tipoUsuario=$tipoUsuario)"
    }


}