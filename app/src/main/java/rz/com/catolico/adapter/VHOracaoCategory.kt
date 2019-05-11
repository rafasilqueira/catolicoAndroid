package rz.com.catolico.adapter

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import rz.com.catolico.R

/**
 * @author Rafael.Tupinamba 11/05/2019
 */

class VHOracaoCategory(private val context: Context, private val mView: View) : RecyclerView.ViewHolder(mView) {

    var llm = LinearLayoutManager(context)
    var txtName = mView.findViewById(R.id.txt_name) as TextView
    var recyclerView = mView.findViewById(R.id.recyclerview) as RecyclerView

    init {
        recyclerView.layoutManager = llm
    }

    fun setOnClickListener(listener: View.OnClickListener?) {
        if (listener != null) {
            this.mView.setOnClickListener(listener)
        }
    }

}
