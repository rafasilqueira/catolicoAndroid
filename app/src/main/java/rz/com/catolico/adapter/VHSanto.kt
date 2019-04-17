package rz.com.catolico.adapter

import android.content.Context
import android.support.v7.widget.AppCompatImageButton
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.View
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
    var txtIsSantoDia: TextView
    var cardView: CardView
    var txtDescricao : TextView
    var favoriteButton : AppCompatImageButton
    var prayButton : AppCompatImageButton
    var shareButton : AppCompatImageButton
    var divideLineOne : View
    var dividerLineTwo : View
    var txtPrayQty : TextView

    init {
        cardView = mView.findViewById(R.id.contact_card_view)
        txtSantoNome = mView.findViewById(R.id.txt_santo_nome)
        txtDiaData = mView.findViewById(R.id.txt_dias_data)
        txtComemoracao = mView.findViewById(R.id.txt_santo_comemoracao)
        imgSanto = mView.findViewById(R.id.img_santo)
        txtIsSantoDia = mView.findViewById(R.id.txt_is_santo_dia)
        txtDescricao = mView.findViewById(R.id.txt_santo_descricao)
        txtDescricao.visibility = View.GONE
        favoriteButton = mView.findViewById(R.id.favorite_button)
        prayButton = mView.findViewById(R.id.pray_button)
        shareButton = mView.findViewById(R.id.share_button)
        divideLineOne = mView.findViewById(R.id.divider_line)
        dividerLineTwo = mView.findViewById(R.id.divider_line_2)
        txtPrayQty = mView.findViewById(R.id.txt_prayers_qty)
    }

    fun setOnClickListener(listener: View.OnClickListener?) {
        if (listener != null) {
            this.mView.setOnClickListener(listener)
        }
    }
}
