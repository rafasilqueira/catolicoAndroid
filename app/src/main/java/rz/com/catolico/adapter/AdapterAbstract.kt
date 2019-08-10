package rz.com.catolico.adapter

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import rz.com.catolico.bean.Usuario
import rz.com.catolico.utils.Constantes

abstract class AdapterAbstract<T>(val context: Context, private var mItems: MutableList<T>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    protected var usuario = (context as Activity).intent.getSerializableExtra(Constantes.USER_KEY) as Usuario?

    abstract fun setupViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
    abstract fun onBindData(holder: RecyclerView.ViewHolder, genericType: T)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = setupViewHolder(parent, viewType)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        onBindData(holder, this.mItems[position])
    }

    override fun getItemCount() = this.mItems.size

    fun getItem(position: Int): T = this.mItems[position]


}