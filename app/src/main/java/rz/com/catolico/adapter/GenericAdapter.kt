package rz.com.catolico.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import rz.com.catolico.utils.ActivityUtils.Companion.isUserLogged

import java.util.ArrayList

abstract class GenericAdapter<T>(val context: Context, mItems: List<T>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val mItems: List<T> = mItems ?: ArrayList()
    protected var isUserLogged : Boolean = isUserLogged(context)

    abstract fun setupViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder

    abstract fun onBindData(holder: RecyclerView.ViewHolder, genericType: T)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return setupViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        onBindData(holder, this.mItems[position])
    }

    override fun getItemCount(): Int {
        return this.mItems.size
    }

    fun getItem(position: Int): T {
        return this.mItems[position]
    }

}