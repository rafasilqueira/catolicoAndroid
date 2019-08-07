package rz.com.catolico.activiy

import android.os.Bundle
import rz.com.catolico.R
import rz.com.catolico.bean.Santo
import rz.com.catolico.interfaces.IAdapter

class ActivitySelectedSanto : ActivityAbstract() , IAdapter<Santo> {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun setupAdapter(mList: MutableList<Santo>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSucessLoadData() {
        super.onSucessLoadData()
    }

    override fun onErrorLoadData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun itemClickListener(type: Santo) {
        super.itemClickListener(type)
    }

}