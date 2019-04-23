package rz.com.catolico.bean

import java.io.Serializable

open class BaseEntityNameDescricao : BaseEntity(), Serializable {
    var nome: String? = null
    var descricao: String? = null
}