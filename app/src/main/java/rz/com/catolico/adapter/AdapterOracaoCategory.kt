package rz.com.catolico.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import rz.com.catolico.R
import rz.com.catolico.bean.Oracao
import rz.com.catolico.fragments.FragmentAbstract
import rz.com.catolico.interfaces.IMap

/**
 * @author Rafael.Tupinamba 11/05/2019
 */

class AdapterOracaoCategory(context: Context, private var map: Map<String, MutableList<Oracao>>) :
        AdapterAbstract<String>(context, map.keys.toMutableList()), IMap<Oracao> {

    private var fragmentAbstract: FragmentAbstract<Oracao>? = null

    constructor(context: Context, fragmentAbstract: FragmentAbstract<Oracao>, map: Map<String, MutableList<Oracao>>) : this(context, map) {
        this.fragmentAbstract = fragmentAbstract
    }

    override fun setupViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.adapter_oracao_a_z_category, parent, false)
        return VHOracaoCategory(context, view)
    }

    override fun onBindData(holder: RecyclerView.ViewHolder, key: String) {
        val view: VHOracaoCategory
        if (holder is VHOracaoCategory) {
            view = holder
            view.txtName.text = key
            if (view.recyclerView.adapter == null) {
                view.recyclerView.adapter = getMapValues(map, key)?.let { AdapterOracao(context, it) }
            }
        }
    }

}