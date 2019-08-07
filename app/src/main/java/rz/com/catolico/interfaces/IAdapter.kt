package rz.com.catolico.interfaces

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import rz.com.catolico.R
import rz.com.catolico.exception.CatolicoException

interface IAdapter<T> {

    companion object {
        const val HORIZONTAL = LinearLayoutManager.HORIZONTAL
        const val VERTICAL = LinearLayoutManager.VERTICAL
    }

    fun setupAdapter(mList: MutableList<T>)
    fun loadData()
    fun onSucessLoadData() {}
    fun onErrorLoadData()
    fun itemClickListener(type: T) {}

    fun getLinearLayoutManager(context: Context,orientation: Int) = when (orientation) {
        HORIZONTAL -> LinearLayoutManager(context, HORIZONTAL, false)
        VERTICAL -> LinearLayoutManager(context, VERTICAL, false)
        else -> throw CatolicoException(context.getString(R.string.invalid_orientation))
    }

}