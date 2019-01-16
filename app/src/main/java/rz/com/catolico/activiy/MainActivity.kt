package rz.com.catolico.activiy

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.facebook.login.LoginManager
import com.google.gson.GsonBuilder
import com.orhanobut.hawk.Hawk
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rz.com.catolico.AsincTasks.UsuarioTask.LoginTask
import rz.com.catolico.R
import rz.com.catolico.bean.Usuario
import rz.com.catolico.interfaces.Usuario.Login
import rz.com.catolico.retrofit.RetrofitConfig
import rz.com.catolico.utils.Constantes.Companion.USER_KEY
import rz.com.catolico.utils.StatusFacebookLogin

class MainActivity : AppCompatActivity(), Login {


    override fun doLoginSucess(usuario: Usuario) {
        //System.out.println(GsonBuilder().setPrettyPrinting().create().toJson(usuario))
        startActivity(Intent(this@MainActivity, CatolicoMainActivity::class.java).putExtra(USER_KEY, usuario))
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
                LoginTask(this, usuario!!, false).execute()
            } else {
                Hawk.delete(USER_KEY)
                startActivity(Intent(this@MainActivity, CatolicoMainActivity::class.java))
                finish()
            }

        }, 1500)

    }
}
