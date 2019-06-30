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
import rz.com.catolico.utils.Constantes

class FragmentSantoRelated : FragmentAbstractAdapter<Oracao, ActivityCatolicoMain>(), IFavorite<Oracao>, ISortOracao {

    private lateinit var santo: Santo
    var fragmentParent: FragmentSelectedSanto? = null


    fun mudar(oracao: Oracao) {
        fragmentParent?.swapFragment(FragmentSelectedOracao.instance(oracao), Constantes.SELECTED_ORACAO_FRAGMENT_TAG)
    }

    companion object {
        fun instance(santo: Santo): FragmentSantoRelated {
            val fragmentSantoRelated = FragmentSantoRelated()
            val bundle = Bundle()
            bundle.putSerializable("santo", santo)
            fragmentSantoRelated.arguments = bundle
            return fragmentSantoRelated
        }

        fun instance(santo: Santo, parentFragment: FragmentSelectedSanto): FragmentSantoRelated {
            val fragmentSantoRelated = FragmentSantoRelated()
            val bundle = Bundle()
            bundle.putSerializable("santo", santo)
            bundle.putSerializable("parent", parentFragment)
            fragmentSantoRelated.arguments = bundle
            return fragmentSantoRelated
        }
    }

    override fun afterAttachFragment() {
        getSerialiableArgumentExtra("santo").let { santo = (it as Santo) }
        fragmentParent = getSerialiableArgumentExtra("parent") as FragmentSelectedSanto
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_oracao, container, false) as ViewGroup
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loadData()
    }

    override fun loadData() {
        val oracoes = santo.oracoes
        if (oracoes.isEmpty()) {
            changeView(R.layout.fragment_santo_related_nothing)
            return
        }

        if (isUserLogged()) {
            getUser()?.let { syncronizeFavorites(it.oracoes, oracoes) }
        }

        setupAdapter(oracoes)
    }

    override fun setupAdapter(mList: MutableList<Oracao>) {
        val recyclerView = view?.findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView?.layoutManager = getLinearLayoutManager(VERTICAL)
        val adapterOracao = AdapterOracao(getParentActivity(), this, mList)
        adapterOracao?.let { recyclerView?.adapter = it }
    }

}