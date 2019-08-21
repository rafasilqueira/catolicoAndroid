package rz.com.catolico.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.orhanobut.hawk.Hawk
import rz.com.catolico.bean.Usuario
import rz.com.catolico.utils.Constantes.Companion.USER_KEY

abstract class AdapterAbstract<T>(val context: Context, private var mItems: MutableList<T>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    protected var usuario: Usuario? = Hawk.get(USER_KEY)
//    protected var usuario = (context as Activity).intent.getSerializableExtra(Constantes.USER_KEY) as Usuario?

    fun getUser(): Usuario? = Hawk.get(USER_KEY)
    abstract fun setupViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
    abstract fun onBindData(holder: RecyclerView.ViewHolder, genericType: T)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = setupViewHolder(parent, viewType)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        onBindData(holder, this.mItems[position])
    }

    override fun getItemCount() = this.mItems.size

    fun getItem(position: Int): T = this.mItems[position]


}