package rz.com.catolico.adapter

import android.content.Context
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import rz.com.catolico.R

/**
 * @author Rafael.Tupinamba 04/05/2019
 */

class VHOracao(private val mContext: Context, private val mView: View) : RecyclerView.ViewHolder(mView) {

    var txtOracao: TextView? = null
    var txtCategoria: TextView? = null
    var favoriteButton: AppCompatImageView? = null

    init {
        txtOracao = mView.findViewById(R.id.txt_oracao)
        txtCategoria = mView.findViewById(R.id.txt_categoria)
        favoriteButton = mView.findViewById(R.id.img_favorite_pray)
    }

    fun setOnClickListener(listener: View.OnClickListener?) {
        if (listener != null) {
            this.mView.setOnClickListener(listener)
        }
    }
}
