package rz.com.catolico.interfaces.Usuario

import rz.com.catolico.Exception.CatolicoException
import rz.com.catolico.bean.Usuario

interface Login {

    fun doLoginSucess(usuario: Usuario)
}