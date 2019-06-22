package rz.com.catolico.interfaces

import rz.com.catolico.bean.Oracao

interface ISortOracao : ISort<Oracao> {

    fun sortByCategory(mItems: MutableList<Oracao>): Map<String, MutableList<Oracao>> {
        return mItems.map { it.categoriaOracao }
                .distinct()
                .sortedBy { it?.name }
                .map {
                    it!!.name to mItems
                            .filter { oracao -> oracao.categoriaOracao == it }
                            .sortedBy { it.name }
                            .toMutableList()
                }
                .toMap()
    }

}