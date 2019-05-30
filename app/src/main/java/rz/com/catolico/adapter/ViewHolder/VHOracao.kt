package rz.com.catolico.adapter.ViewHolder

import android.content.Context
import android.view.View
import android.widget.TextView
import rz.com.catolico.R

/**
 * @author Rafael.Tupinamba 04/05/2019
 */

class VHOracao(val context: Context, view: View) : VHShareFavorite(context, view) {

    var txtOracao: TextView = view.findViewById(R.id.txt_oracao)
    var txtCategoria: TextView = view.findViewById(R.id.txt_categoria)
    var dividerLine: View = view.findViewById(R.id.divider_line)

}
