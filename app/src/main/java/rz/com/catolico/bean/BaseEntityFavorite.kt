package rz.com.catolico.bean

import java.io.Serializable

open class BaseEntityFavorite : BaseEntityNameDescricao(), Serializable {
    var favorite = false
}