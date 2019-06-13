package rz.com.catolico.bean

import java.io.Serializable

open class BaseEntityNameDescricao : BaseEntity(), Serializable {
    var name: String = ""
    var descricao: String = ""
}