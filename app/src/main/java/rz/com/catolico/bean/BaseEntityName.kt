package rz.com.catolico.bean

import java.io.Serializable

open class BaseEntityName: BaseEntity(),Serializable {
    var nome: String? = null
}