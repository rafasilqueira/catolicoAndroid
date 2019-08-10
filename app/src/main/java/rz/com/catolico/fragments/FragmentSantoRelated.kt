package rz.com.catolico.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import rz.com.catolico.R
import rz.com.catolico.adapter.AdapterOracao
import rz.com.catolico.bean.Oracao
import rz.com.catolico.bean.Santo
import rz.com.catolico.interfaces.IAdapter
import rz.com.catolico.interfaces.IAdapter.Companion.VERTICAL
import rz.com.catolico.interfaces.ISortOracao
import rz.com.catolico.utils.Constantes

class FragmentSantoRelated : Fragment(), ISortOracao, IAdapter<Oracao> {

    private lateinit var santo: Santo
    var fragmentParent: FragmentSelectedSanto? = null


    /*fun mudar(oracao: Oracao) {
        fragmentParent?.swapFragment(FragmentSelectedOracao.instance(oracao), Constantes.SELECTED_ORACAO_FRAGMENT_TAG)
    }*/

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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_oracao, container, false) as ViewGroup
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.getSerializable("santo").let { santo = (it as Santo) }
        loadData()
    }

    override fun loadData() {
        onSucessLoadData(santo.oracoes)
        /*if (oracoes.isEmpty()) {
            changeView(R.layout.fragment_santo_related_nothing)
            return
        }*/

    }

    override fun setupAdapter(mList: MutableList<Oracao>) {
        val recyclerView = view?.findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView?.layoutManager = getLinearLayoutManager(activity!!, VERTICAL)
        val adapterOracao = AdapterOracao(activity!!, mList)
        recyclerView?.adapter = adapterOracao
    }

    override fun onSucessLoadData(list: MutableList<Oracao>) {
        setupAdapter(list)
    }


    override fun onErrorLoadData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemClick(type: Oracao) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}