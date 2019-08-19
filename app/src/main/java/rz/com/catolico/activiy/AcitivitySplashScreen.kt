package rz.com.catolico.activiy

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.orhanobut.hawk.Hawk
import rz.com.catolico.R
import rz.com.catolico.bean.Usuario
import rz.com.catolico.interfaces.ILogin
import rz.com.catolico.utils.Constantes.Companion.USER_KEY

class AcitivitySplashScreen : AppCompatActivity(), ILogin {


    override fun doLoginSucess(usuario: Usuario) {
        startActivity(Intent(this@AcitivitySplashScreen, ActivityCatolicoMain::class.java)
                .putExtra(USER_KEY, usuario)
                .putExtra("drawer", intent.getBooleanExtra("drawer", false))
        )
        finish()
    }

    private var usuario: Usuario? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Hawk.init(applicationContext).build()
        val handle = Handler()
        handle.postDelayed({
            usuario = Hawk.get<Usuario>(USER_KEY)
            //println(usuario)
            if (usuario != null) {
                //println(Gson().toJson(usuario))
                doLogin(usuario!!, this, false)
            } else {
                Hawk.delete(USER_KEY)
                startActivity(Intent(this@AcitivitySplashScreen, ActivityCatolicoMain::class.java))
                finish()
            }

        }, 1500)

    }

}
