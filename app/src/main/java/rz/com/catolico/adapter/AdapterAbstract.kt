package rz.com.catolico.adapter

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import rz.com.catolico.bean.Usuario
import rz.com.catolico.utils.ActivityUtils.Companion.isUserLogged
import rz.com.catolico.utils.Constantes
import java.util.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.Map
import kotlin.collections.MutableList

abstract class AdapterAbstract<T>(val context: Context, private var mItems: MutableList<T>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    protected var isUserLogged: Boolean = isUserLogged(context)
    protected var usuario = (context as Activity).intent.getSerializableExtra(Constantes.USER_KEY) as? Usuario

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