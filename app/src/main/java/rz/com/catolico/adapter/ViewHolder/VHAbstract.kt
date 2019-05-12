package rz.com.catolico.adapter.ViewHolder

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View

abstract class VHAbstract(val abstractContext: Context, val abstractView: View) : RecyclerView.ViewHolder(abstractView) {

    fun setOnClickListener(listener: View.OnClickListener?) {
        if (listener != null) {
            this.abstractView.setOnClickListener(listener)
        }
    }
}