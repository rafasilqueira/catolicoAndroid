package rz.com.catolico.adapter.ViewHolder

import android.content.Context
import android.support.v7.widget.AppCompatImageButton
import android.view.View
import rz.com.catolico.R

open class VHShareFavorite(private val context: Context, val view: View) : VHAbstract(context, view) {
    var favoriteButton: AppCompatImageButton? = view.findViewById(R.id.favorite_button) ?: null
    var shareButton: AppCompatImageButton? = view.findViewById(R.id.share_button) ?: null
}