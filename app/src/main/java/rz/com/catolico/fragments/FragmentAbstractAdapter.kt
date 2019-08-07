package rz.com.catolico.fragments

import android.view.View
import rz.com.catolico.R
import rz.com.catolico.activiy.ActivityBaseFragment
import rz.com.catolico.interfaces.IAdapter

abstract class FragmentAbstractAdapter<T, A : ActivityBaseFragment> : FragmentAbstract<A>(), IAdapter<T> {


    protected var mList: MutableList<T> = ArrayList()

    abstract fun onItemClick(type: T)

    override fun onErrorLoadData() {
        disableAllIcons()
        val view: View? = view?.findViewById(R.id.root_layout)
        view?.let {
            it.setOnClickListener {
                loadData()
            }
        }
    }

}