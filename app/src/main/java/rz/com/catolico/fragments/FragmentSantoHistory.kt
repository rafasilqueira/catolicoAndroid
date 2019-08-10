package rz.com.catolico.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import rz.com.catolico.R
import rz.com.catolico.bean.Santo

class FragmentSantoHistory : Fragment() {

    companion object {
        fun instance(santo: Santo): FragmentSantoHistory {
            val fragmentSantoHistory = FragmentSantoHistory()
            val bundle = Bundle()
            bundle.putSerializable("santo", santo)
            fragmentSantoHistory.arguments = bundle
            return fragmentSantoHistory
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_santo_history, container, false)
        val santo = arguments?.getSerializable("santo") as Santo
        view.findViewById<TextView>(R.id.txtName).text = santo.name
        view.findViewById<TextView>(R.id.txtHistory).text = santo.descricao
        return view
    }

}