package rz.com.catolico.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
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

class FragmentSantoRelated : FragmentAbstract<ActivityCatolicoMain>(), IFavorite<Oracao>, ISortOracao {

    private var recyclerview : RecyclerView? = null

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
        val view = inflater.inflate(R.layout.fragment_oracao, container, false) as ViewGroup
        /*mInflater = inflater
        mContainer = container*/
        recyclerview = view.findViewById<RecyclerView>(R.id.recyclerview)
        loadData()
        return view
    }

    private fun loadData() {
        val oracoes = (arguments?.getSerializable("santo") as Santo).oracoes
        /*if (oracoes.isNotEmpty()) {
            usuario?.let { syncronizeFavorites(it.oracoes, oracoes) }
        }*/
            setupAdapter(oracoes)

    }

    private fun setupAdapter(list: MutableList<Oracao>) {
        recyclerview?.layoutManager = LinearLayoutManager((activity as Context), LinearLayoutManager.VERTICAL, false)
        val adapterOracao = activity?.let { AdapterOracao(it, sortAlphabetical(list)) }
        adapterOracao?.let { recyclerview?.adapter = it }
    }

}