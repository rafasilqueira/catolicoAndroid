package rz.com.catolico.fragments

import android.os.Bundle
import rz.com.catolico.R
import rz.com.catolico.activiy.ActivityCatolicoMain
import rz.com.catolico.adapter.AdapterOracao
import rz.com.catolico.bean.Oracao
import rz.com.catolico.bean.Santo
import rz.com.catolico.interfaces.IFavorite
import rz.com.catolico.interfaces.ISortOracao

class FragmentSantoRelated : FragmentAbstractAdapter<Oracao, ActivityCatolicoMain>(R.layout.fragment_oracao), IFavorite<Oracao>, ISortOracao {

    companion object {
        fun instance(santo: Santo): FragmentSantoRelated {
            val fragmentSantoRelated = FragmentSantoRelated()
            val bundle = Bundle()
            bundle.putSerializable("santo", santo)
            fragmentSantoRelated.arguments = bundle
            return fragmentSantoRelated
        }
    }

    override fun loadData() {
        val oracoes = (arguments?.getSerializable("santo") as Santo).oracoes
        if (oracoes.isNotEmpty()) {
            usuario?.let { syncronizeFavorites(it.oracoes, oracoes) }
            setupAdapter(oracoes)
        }

    }

    override fun setupAdapter(mItems: MutableList<Oracao>) {
        setupRecyclerView()
        val adapterOracao = parentActivity?.let { AdapterOracao(it, sortAlphabetical(mItems)) }
        adapterOracao?.let { recyclerView?.adapter = it }
    }

    override fun itemClickListener(type: Oracao) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}