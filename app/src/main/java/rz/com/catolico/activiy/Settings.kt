package rz.com.catolico.activiy

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import rz.com.catolico.bean.Usuario
import rz.com.catolico.utils.Constantes.Companion.USER_KEY

class Settings : AppCompatActivity() {

    private var usuario: Usuario? = null

    fun setUsuario(usuario: Usuario){
        this.usuario = usuario;
    }

    override fun onBackPressed() {
        setResult(Activity.RESULT_OK,Intent().putExtra(USER_KEY,usuario))
        super.onBackPressed()
    }

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