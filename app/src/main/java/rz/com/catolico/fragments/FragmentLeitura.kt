package rz.com.catolico.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import rz.com.catolico.R
import rz.com.catolico.bean.Leitura
import rz.com.catolico.utils.Constantes.Companion.LITURGIA_FRAGMENT_CONTENT_TAG

class FragmentLeitura : Fragment() {


    companion object {
        fun instance(leitura: Leitura): FragmentLeitura {
            var fragmentLeitura = FragmentLeitura()
            var bundle = Bundle()
            bundle.putSerializable(LITURGIA_FRAGMENT_CONTENT_TAG, leitura)
            fragmentLeitura.arguments = bundle
            return fragmentLeitura
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_leitura, container, false)
        val leitura = arguments?.getSerializable(LITURGIA_FRAGMENT_CONTENT_TAG) as Leitura
        view.findViewById<TextView>(R.id.txtPassagem).text = leitura.passagem
        view.findViewById<TextView>(R.id.txtDescricao).text = leitura.descricao
        return view
    }

}