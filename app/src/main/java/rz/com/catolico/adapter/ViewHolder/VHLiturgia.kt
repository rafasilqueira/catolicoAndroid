package rz.com.catolico.adapter.ViewHolder

import android.content.Context
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import rz.com.catolico.R

class VHLiturgia(val context: Context, view: View) : VHShareFavorite(context, view) {

    var txtMonth: TextView = view.findViewById(R.id.txtMonth)
    var txtDay: TextView = view.findViewById(R.id.txtDay)
    var frameLayout: FrameLayout = view.findViewById(R.id.frameLayout)

}