package rz.com.catolico.adapter

import android.content.Context
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView

import rz.com.catolico.R

/**
 * @author Rafael.Tupinamba 05/04/2019
 */

class VHSanto(private val mContext: Context, private val mView: View) : RecyclerView.ViewHolder(mView) {

    var txtSantoNome: TextView
    var txtDiaData: TextView
    var txtComemoracao: TextView
    var imgSanto: AppCompatImageView
    var txtIsSantoDia : TextView

    init {
        txtSantoNome = mView.findViewById(R.id.txt_santo_nome)
        txtDiaData = mView.findViewById(R.id.txt_dias_data)
        txtComemoracao = mView.findViewById(R.id.txt_santo_comemoracao)
        imgSanto = mView.findViewById(R.id.img_santo)
        txtIsSantoDia = mView.findViewById(R.id.txt_is_santo_dia)
    }

    fun setOnClickListener(listener: View.OnClickListener?) {
        if (listener != null) {
            this.mView.setOnClickListener(listener)
        }
    }
}
