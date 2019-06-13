package rz.com.catolico.adapter

import android.support.v4.app.FragmentManager
import rz.com.catolico.bean.Leitura
import rz.com.catolico.fragments.FragmentLeitura

class AdapterViewPagerLeitura(manager: FragmentManager) : AdapterAbstractViewPager<Leitura>(manager) {

    override fun addAllFrags(mItems: MutableList<Leitura>) {
        mItems.forEach { addFrag(FragmentLeitura.instance(it), it.tipoLeitura.name) }
        //notifyDataSetChanged()
    }
}