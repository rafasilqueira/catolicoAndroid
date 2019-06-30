package rz.com.catolico.fragments

import android.support.v7.widget.LinearLayoutManager
import rz.com.catolico.R
import rz.com.catolico.activiy.ActivityBaseFragment
import rz.com.catolico.exception.CatolicoException

abstract class FragmentAbstractAdapter<T, A : ActivityBaseFragment> : FragmentAbstract<A>() {

    companion object {
        const val HORIZONTAL = LinearLayoutManager.HORIZONTAL
        const val VERTICAL = LinearLayoutManager.VERTICAL
    }

    protected var mList: MutableList<T> = ArrayList()

    abstract fun setupAdapter(mList: MutableList<T>)
    abstract fun loadData()

    open fun onSucessLoadData() {}
    open fun onErrorLoadData() {}
    open fun itemClickListener(type: T) {}

    protected fun getLinearLayoutManager(orientation: Int) = when (orientation) {
        HORIZONTAL -> LinearLayoutManager(getParentActivity().applicationContext, HORIZONTAL, false)
        VERTICAL -> LinearLayoutManager(getParentActivity().applicationContext, VERTICAL, false)
        else -> throw CatolicoException(getString(R.string.invalid_orientation))
    }

}