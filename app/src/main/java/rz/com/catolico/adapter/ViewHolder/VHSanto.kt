package rz.com.catolico.adapter.ViewHolder

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

class VHSanto(private val context: Context, view: View) : VHShareFavorite(context,view) {

    var txtSantoNome: TextView = view.findViewById(R.id.txt_santo_nome)
    var txtDiaData: TextView = view.findViewById(R.id.txt_dias_data)
    var txtComemoracao: TextView = view.findViewById(R.id.txt_santo_comemoracao)
    var imgSanto: AppCompatImageView = view.findViewById(R.id.img_santo)
    var txtIsSantoDia: TextView = view.findViewById(R.id.txt_is_santo_dia)
    var cardView: CardView = view.findViewById(R.id.contact_card_view)
    var txtDescricao : TextView = view.findViewById(R.id.txt_santo_descricao)
    var prayButton : AppCompatImageButton = view.findViewById(R.id.pray_button)
    var divideLineOne : View  = view.findViewById(R.id.divider_line)
    var dividerLineTwo : View = view.findViewById(R.id.divider_line_2)
    var txtPrayQty : TextView = view.findViewById(R.id.txt_prayers_qty)

    init {
        txtDescricao.visibility = View.GONE
    }

}
