package rz.com.catolico.activiy

import android.support.v7.app.AppCompatActivity
import com.orhanobut.hawk.Hawk
import rz.com.catolico.bean.Usuario
import rz.com.catolico.utils.Constantes.Companion.USER_KEY
import java.io.Serializable

abstract class ActivityAbstract : AppCompatActivity() {

    protected fun isUserLogged() = getUser() != null

    fun getUser(): Usuario? = Hawk.get<Usuario>(USER_KEY)

    fun getIntentObject(key: String): Serializable? = intent.getSerializableExtra(key)

    fun setActionBarTitle(title: String) {
        supportActionBar?.title = title
    }

    open fun setupMenuBarIcons() {}

}