package rz.com.catolico.utils

import android.content.Context
import android.widget.Toast
import rz.com.catolico.R

class ToastMisc {

    companion object {
        fun generalError(context : Context) = Toast.makeText(context, R.string.general_error,Toast.LENGTH_SHORT).show()
        fun userNotFound(context :Context) = Toast.makeText(context, R.string.user_not_found, Toast.LENGTH_SHORT).show()
    }
}