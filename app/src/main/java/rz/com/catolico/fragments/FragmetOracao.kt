package rz.com.catolico.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import rz.com.catolico.bean.Oracao

class FragmetOracao : FragmentAbstract<Oracao>() {

    override fun setupAdapter(list: MutableList<Oracao>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun itemClickListenr(type: Oracao) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadData() {

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }


}