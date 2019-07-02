package rz.com.catolico.interfaces

interface IMap<T> {

    fun getMapValues(map: Map<String, MutableList<T>> , key:String) : MutableList<T>? {
        return map[key]
    }

}