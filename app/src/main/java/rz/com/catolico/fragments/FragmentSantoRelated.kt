package rz.com.catolico.fragments

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import rz.com.catolico.R
import rz.com.catolico.activiy.ActivityCatolicoMain
import rz.com.catolico.adapter.AdapterOracao
import rz.com.catolico.bean.Oracao
import rz.com.catolico.bean.Santo
import rz.com.catolico.interfaces.IFavorite
import rz.com.catolico.interfaces.ISortOracao

class FragmentSantoRelated : FragmentAbstractAdapter<Oracao, ActivityCatolicoMain>(), IFavorite<Oracao>, ISortOracao {

    companion object {
        fun instance(santo: Santo): FragmentSantoRelated {
            val fragmentSantoRelated = FragmentSantoRelated()
            val bundle = Bundle()
            bundle.putSerializable("santo", santo)
            fragmentSantoRelated.arguments = bundle
            return fragmentSantoRelated
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_oracao, container, false) as ViewGroup
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loadData()
    }

    override fun loadData() {
        val oracoes = (arguments?.getSerializable("santo") as Santo).oracoes
        if (oracoes.isNotEmpty()) {
            getUser()?.let { syncronizeFavorites(it.oracoes, oracoes) }
        }
        setupAdapter(oracoes)

    }

    override fun setupAdapter(list: MutableList<Oracao>) {
        val recyclerView = view?.findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView?.layoutManager = getLinearLayoutManager(VERTICAL)
        val adapterOracao = activity?.let { AdapterOracao(it, sortAlphabetical(list)) }
        adapterOracao?.let { recyclerView?.adapter = it }
    }

}