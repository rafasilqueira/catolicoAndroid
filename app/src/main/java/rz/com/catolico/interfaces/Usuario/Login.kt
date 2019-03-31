package rz.com.catolico.interfaces.Usuario

import br.com.tupinamba.model.bean.Usuario

interface Login {

    fun doLoginSucess(usuario: Usuario)
}