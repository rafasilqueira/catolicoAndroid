package rz.com.catolico.interfaces

import rz.com.catolico.bean.BaseEntityNameDescricao

interface ISort<T : BaseEntityNameDescricao> {

    fun sortAlphabeticalMap(mItems: MutableList<T>): Map<String, MutableList<T>> {
        return mItems.map { it.name[0] }
                .distinct()
                .sorted()
                .map {
                    it.toString() to mItems
                            .filter { oracao -> oracao.name[0] == it }
                            .sortedBy { it.name }
                            .toMutableList()
                }
                .toMap()
    }

    fun sortAlphabetical(mItems: MutableList<T>): MutableList<T> {
        return mItems.sortedBy { it.name }.toMutableList()
    }
}