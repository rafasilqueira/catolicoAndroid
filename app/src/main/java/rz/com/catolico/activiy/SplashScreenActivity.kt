package rz.com.catolico.activiy

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.google.gson.Gson
import com.orhanobut.hawk.Hawk
import rz.com.catolico.asincTasks.UsuarioTask.LoginTask
import rz.com.catolico.R
import rz.com.catolico.bean.Usuario
import rz.com.catolico.interfaces.Usuario.Login
import rz.com.catolico.utils.Constantes.Companion.USER_KEY

class SplashScreenActivity : AppCompatActivity(), Login {


    override fun doLoginSucess(usuario: Usuario) {
        //System.out.println(GsonBuilder().setPrettyPrinting().create().toJson(usuario))
        startActivity(Intent(this@SplashScreenActivity, CatolicoMainActivity::class.java).putExtra(USER_KEY, usuario))
        finish()
    }

    private var usuario: Usuario? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Hawk.init(applicationContext).build()
        val handle = Handler()
        handle.postDelayed({
            usuario = Hawk.get<Any>(USER_KEY) as Usuario?
            println(usuario)
            if (usuario != null) {
                println(Gson().toJson(usuario))
                LoginTask(this, usuario!!, false).execute()
            } else {
                Hawk.delete(USER_KEY)
                startActivity(Intent(this@SplashScreenActivity, CatolicoMainActivity::class.java))
                finish()
            }

        }, 1500)

    }
}
