package rz.com.catolico.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import rz.com.catolico.R
import rz.com.catolico.activiy.ActivityCatolicoMain
import rz.com.catolico.bean.Oracao

class FragmentOracaoContent : Fragment() {

    private var parentContext: ActivityCatolicoMain? = null
    private var txtOracao: TextView? = null
    private var txtDescricao: TextView? = null

    companion object {
        fun instance(oracao: Oracao): FragmentOracaoContent {
            var fragmentOracaoContent = FragmentOracaoContent()
            var bundle = Bundle()
            bundle.putSerializable("oracao", oracao)
            fragmentOracaoContent.arguments = bundle
            return fragmentOracaoContent
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        parentContext = context as ActivityCatolicoMain
        parentContext!!.showIconsOracaoContent()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_oracao_content, container, false) as ViewGroup
        var oracao = arguments?.getSerializable("oracao") as Oracao
        txtOracao = view.findViewById(R.id.txt_oracao)
        txtDescricao = view.findViewById(R.id.txt_descricao_oracao)
        txtOracao?.text = oracao.nome
        txtDescricao?.text = oracao.descricao
        parentContext?.isPrayFavorite(oracao.favorite)
        return view
    }

}