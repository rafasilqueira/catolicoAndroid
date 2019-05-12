package rz.com.catolico.adapter.ViewHolder

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import rz.com.catolico.R

/**
 * @author Rafael.Tupinamba 11/05/2019
 */

class VHOracaoCategory(val context: Context, val view: View) : VHAbstract(context, view) {

    var llm = LinearLayoutManager(context)
    var txtName = view.findViewById(R.id.txt_name) as TextView
    var recyclerView = view.findViewById(R.id.recyclerview) as RecyclerView
    var txtPrayQty = view.findViewById(R.id.txt_prayers_qty) as TextView

    init {
        txtPrayQty.visibility = View.GONE
        recyclerView.layoutManager = llm
    }



}
