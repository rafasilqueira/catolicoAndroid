package rz.com.catolico.bean

import java.io.Serializable

open class BaseEntityNameDescricao : BaseEntity(), Serializable {
    var nome: String = ""
    var descricao: String = ""
}