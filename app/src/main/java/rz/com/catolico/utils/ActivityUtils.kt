package rz.com.catolico.utils

import android.content.Context
import com.orhanobut.hawk.Hawk

class ActivityUtils {
    companion object {
        fun getResourceString(context: Context, id: Int): String {
            return context.getString(id)
        }

        fun isUserLogged(context: Context): Boolean {
            Hawk.init(context).build()
            return Hawk.get<Any>(Constantes.USER_KEY) != null
        }

    }
}