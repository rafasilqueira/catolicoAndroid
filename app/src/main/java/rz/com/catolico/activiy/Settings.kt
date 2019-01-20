package rz.com.catolico.activiy

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import rz.com.catolico.bean.Usuario
import rz.com.catolico.utils.Constantes.Companion.USER_KEY

class Settings : AppCompatActivity() {

    private var usuario: Usuario? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        usuario = intent.getSerializableExtra(USER_KEY) as Usuario
        val fragment = ConfigFragmet.createInstance(usuario!!)
        fragmentManager
                .beginTransaction()
                .replace(android.R.id.content, fragment, "configFragment")
                .commit()

    }
}