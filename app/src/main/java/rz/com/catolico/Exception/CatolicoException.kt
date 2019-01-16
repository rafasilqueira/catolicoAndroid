package rz.com.catolico.Exception

class CatolicoException(message: String?) : RuntimeException(message) {

    constructor(message: String?,throwable: Throwable) : this(message)


}