package rz.com.catolico.utils

import android.content.Context
import com.facebook.AccessToken
import com.facebook.FacebookSdk

class StatusFacebookLogin {

    companion object {
        fun isFacebookLoggedIn(context: Context): Boolean {
            FacebookSdk.sdkInitialize(context.applicationContext)
            return AccessToken.isCurrentAccessTokenActive() != null
        }
    }
}