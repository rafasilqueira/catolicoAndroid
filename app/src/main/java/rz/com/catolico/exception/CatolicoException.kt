package rz.com.catolico.exception

class CatolicoException(message: String) : RuntimeException(message) {
    constructor(message: String, throwable: Throwable) : this(message)
}