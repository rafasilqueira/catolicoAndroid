package rz.com.catolico.utils

import android.content.Context

class ActivityUtils{
    companion object {
        fun getResourceString(context: Context , id: Int) : String{
            return context.getString(id)
        }
    }
}