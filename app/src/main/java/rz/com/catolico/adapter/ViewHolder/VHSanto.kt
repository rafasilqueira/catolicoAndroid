package rz.com.catolico.adapter.ViewHolder

import android.content.Context
import android.support.v7.widget.AppCompatImageButton
import android.support.v7.widget.AppCompatImageView
import android.view.View
import android.widget.TextView
import rz.com.catolico.R

/**
 * @author Rafael.Tupinamba 05/04/2019
 */

class VHSanto(context: Context, view: View) : VHShareFavorite(context, view) {
    var txtSantoNome: TextView = view.findViewById(R.id.txtNome)
    var txtComemoracao: TextView = view.findViewById(R.id.txtComemoracao)
    var imgSanto: AppCompatImageView = view.findViewById(R.id.imgSanto)
    var txtAsterisco: TextView = view.findViewById(R.id.txtAsterisco)
    var prayButton: AppCompatImageButton = view.findViewById(R.id.prayButton)
    var divideLineOne: View = view.findViewById(R.id.divider_line)
    var dividerLineTwo: View = view.findViewById(R.id.divider_line_2)
    var txtPrayQty: TextView = view.findViewById(R.id.txtPrayQty)
}
