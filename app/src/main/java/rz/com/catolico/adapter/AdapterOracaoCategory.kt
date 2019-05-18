package rz.com.catolico.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import rz.com.catolico.R
import rz.com.catolico.adapter.ViewHolder.VHOracaoCategory
import rz.com.catolico.bean.Oracao
import rz.com.catolico.fragments.FragmentAbstract
import rz.com.catolico.interfaces.IMap

/**
 * @author Rafael.Tupinamba 11/05/2019
 */

class AdapterOracaoCategory(context: Context,  var fragmentAbstract: FragmentAbstract<*>, private var map: Map<String, MutableList<Oracao>>) :
        AdapterAbstract<String>(context, map.keys.toMutableList()), IMap<Oracao> {

    override fun setupViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.adapter_oracao_a_z_category, parent, false)
        return VHOracaoCategory(context, view)
    }

    override fun onBindData(holder: RecyclerView.ViewHolder, key: String) {
        val view: VHOracaoCategory
        if (holder is VHOracaoCategory) {
            var oracoes = getMapValues(map, key)
            view = holder
            view.txtName.text = key
            view.txtPrayQty.text = " %02d ".format(oracoes?.size)
            if (view.recyclerView.adapter == null) {
                view.recyclerView.adapter = oracoes?.let { AdapterOracao(context,fragmentAbstract, it,view) }
            }


            view.setOnClickListener(View.OnClickListener {
                if (view.txtPrayQty.visibility == View.GONE) {
                    view.recyclerView.visibility = View.GONE
                    view.txtPrayQty.visibility = View.VISIBLE
                } else {
                    view.recyclerView.visibility = View.VISIBLE
                    view.txtPrayQty.visibility = View.GONE
                }
            })
        }
    }



}