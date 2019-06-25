package rz.com.catolico.fragments

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import rz.com.catolico.R
import rz.com.catolico.activiy.ActivityBaseFragment
import rz.com.catolico.interfaces.IBaseFragmentActivty

abstract class FragmentAbstractAdapter<T, A : ActivityBaseFragment>(initialView: Int) : FragmentAbstract<A>(initialView) {

    protected var recyclerView: RecyclerView? = null
    protected var mList: MutableList<T> = ArrayList()

    abstract fun loadData()
    abstract fun setupAdapter(list: MutableList<T>)
    abstract fun itemClickListener(type: T)


    protected fun setupRecyclerView(linearLayoutManager: LinearLayoutManager = LinearLayoutManager((parentActivity as Context), LinearLayoutManager.VERTICAL, false)) {
        recyclerView = view?.findViewById(R.id.recyclerview)
        recyclerView?.layoutManager = linearLayoutManager
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        view = inflater.inflate(initialView, container, false) as ViewGroup
        mInflater = inflater
        mContainer = container
        loadData()
        return view
    }

}