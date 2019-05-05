package rz.com.catolico.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import rz.com.catolico.R
import rz.com.catolico.activiy.ActivityCatolicoMain

abstract class FragmentAbstract<T>(viewToLoad :Int) : Fragment() {

    protected var parentActivity: ActivityCatolicoMain? = null
    protected var recyclerView: RecyclerView? = null
    protected var mList: MutableList<T>? = null
    private var view: ViewGroup? = null
    private var mInflater: LayoutInflater? = null
    private var mContainer: ViewGroup? = null
    private var initialView = viewToLoad

    abstract fun loadData()

    abstract fun setupAdapter(list: MutableList<T>)

    abstract fun itemClickListenr(type: T)

    fun disableAllIcons(){
        parentActivity?.disableAllFragmentIcons()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        this.parentActivity = context as ActivityCatolicoMain
        parentActivity?.setupFragmentMenuIcon(this@FragmentAbstract)
    }

    fun changeView(layout: Int) {
        val newView = mInflater?.inflate(layout, mContainer, false)
        view?.removeAllViews()
        view?.addView(newView)
    }

    protected fun setupRecyclerView() {
        recyclerView = view?.findViewById(R.id.recyclerview)
        var llm = LinearLayoutManager(parentActivity)
        llm.orientation = LinearLayoutManager.VERTICAL
        recyclerView?.layoutManager = llm
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        view = inflater.inflate(initialView, container, false) as ViewGroup
        mInflater = inflater
        mContainer = container
        loadData()
        return view
    }


}