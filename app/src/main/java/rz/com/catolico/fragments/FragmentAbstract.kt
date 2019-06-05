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
import rz.com.catolico.bean.Usuario

abstract class FragmentAbstract<T>(viewToLoad: Int) : Fragment() {

    protected var parentActivity: ActivityCatolicoMain? = null
    protected var recyclerView: RecyclerView? = null
    protected var mList: MutableList<T> = ArrayList()
    private var view: ViewGroup? = null
    private var mInflater: LayoutInflater? = null
    private var mContainer: ViewGroup? = null
    var initialView = viewToLoad
    protected var usuario: Usuario? = null

    abstract fun loadData()

    abstract fun setupAdapter(list: MutableList<T>)

    abstract fun itemClickListenr(type: T)

    fun disableAllIcons() {
        parentActivity?.disableAllFragmentIcons()
    }


    fun swapFragment(fragment: Fragment, TAG: String) {
        fragmentManager!!.beginTransaction()
                .hide(this)
                .add(R.id.frame_layout, fragment, TAG)
                .addToBackStack(null)
                .commit()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        parentActivity = context as ActivityCatolicoMain
        parentActivity?.setupFragmentMenuIcon(this@FragmentAbstract)
        usuario = parentActivity?.getIntentUser()
    }

    fun changeView(layout: Int) {
        val newView = mInflater?.inflate(layout, mContainer, false)
        view?.removeAllViews()
        view?.addView(newView)
    }

    protected fun setupRecyclerView(linearLayoutManager: LinearLayoutManager = LinearLayoutManager(parentActivity, LinearLayoutManager.VERTICAL, false)) {
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