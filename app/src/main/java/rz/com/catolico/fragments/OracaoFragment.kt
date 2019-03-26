package rz.com.catolico.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import rz.com.catolico.R

class OracaoFragment : Fragment() {

    private var view : ViewGroup? = null


    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
         view = inflater.inflate(R.layout.fragment_oracoes,container,false) as ViewGroup?
        return view
    }

}