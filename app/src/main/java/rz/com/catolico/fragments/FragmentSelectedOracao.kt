package rz.com.catolico.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import rz.com.catolico.R
import rz.com.catolico.activiy.ActivityCatolicoMain
import rz.com.catolico.bean.Oracao
import rz.com.catolico.interfaces.IFavoriteOracao
import rz.com.catolico.interfaces.ISelectableContent

class FragmentSelectedOracao : FragmentAbstract<ActivityCatolicoMain>(), IFavoriteOracao, ISelectableContent {

    companion object {
        fun instance(oracao: Oracao): FragmentSelectedOracao {
            val fragmentSelectedOracao = FragmentSelectedOracao()
            val bundle = Bundle()
            bundle.putSerializable("oracao", oracao)
            fragmentSelectedOracao.arguments = bundle
            return fragmentSelectedOracao
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_selected_oracao, container, false) as ViewGroup
        val oracao = getOracao("oracao")

        oracao.let {
            view.findViewById<TextView>(R.id.txtOracao)?.text = it.name
            view.findViewById<TextView>(R.id.txtDescricao)?.text = it.descricao
            getParentActivity().isFavorite(it.favorite)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        disableAllIcons()
        getParentActivity().showIconsSelectedContent()
    }

    private fun getOracao(key: String): Oracao = getSerialiableArgumentExtra(key) as Oracao

    override fun onShareListener() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onFavoriteListener() {
        val oracao = getOracao("oracao")
        getUser()?.let { onUpdateFavorite(oracao, it) }
    }

    override fun onSucessUpdateFavorite(type: Oracao) {
        getParentActivity().isFavorite(type.favorite)
    }


}