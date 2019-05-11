package rz.com.catolico.adapter

import android.content.Context
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import org.w3c.dom.Text
import rz.com.catolico.R

/**
 * @author Rafael.Tupinamba 04/05/2019
 */

class VHOracao(private val mContext: Context, private val mView: View) : RecyclerView.ViewHolder(mView) {

    var txtOracao : TextView? = mView.findViewById(R.id.txt_oracao) ?: null
    var txtCategoria : TextView? = mView.findViewById(R.id.txt_categoria) ?: null
    var favoriteButton : AppCompatImageView? = mView.findViewById(R.id.img_favorite_pray) ?: null
    var dividerLine : View? = mView.findViewById(R.id.divider_line) ?: null

    fun setOnClickListener(listener: View.OnClickListener?) {
        if (listener != null) {
            this.mView.setOnClickListener(listener)
        }
    }
}
