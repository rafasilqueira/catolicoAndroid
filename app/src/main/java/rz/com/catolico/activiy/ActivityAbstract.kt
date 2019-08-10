package rz.com.catolico.activiy

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import rz.com.catolico.bean.Usuario
import rz.com.catolico.utils.Constantes
import java.io.Serializable

abstract class ActivityAbstract : AppCompatActivity() {

    fun getIntentUser(activity: Activity): Usuario? {
        activity.intent.getSerializableExtra(Constantes.USER_KEY)?.let { return it as Usuario }
        return null
    }

    fun getIntentObject(key: String): Serializable? = intent.getSerializableExtra(key)

    fun setActionBarTitle(title: String) {
        supportActionBar?.title = title
    }

}