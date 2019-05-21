package rz.com.catolico.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import rz.com.catolico.R
import rz.com.catolico.bean.Liturgia

class FragmentLiturgia : FragmentAbstract<Liturgia>(R.layout.fragment_liturgia){

    companion object {
        fun instance() : FragmentLiturgia{
            return FragmentLiturgia()
        }
    }


    override fun loadData() {
        println("aqui")
    }

    override fun setupAdapter(list: MutableList<Liturgia>) {
        println("aqui")
    }

    override fun itemClickListenr(type: Liturgia) {
        println("aqui")
    }


}