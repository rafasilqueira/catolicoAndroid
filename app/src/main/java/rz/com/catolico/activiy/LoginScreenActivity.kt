package rz.com.catolico.activiy

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Base64
import android.util.Log
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.GraphRequest
import com.facebook.login.LoginResult
import com.orhanobut.hawk.Hawk
import kotlinx.android.synthetic.main.activity_login_screen.*
import org.json.JSONException
import rz.com.catolico.AsincTasks.UsuarioTask.LoginTask
import rz.com.catolico.Exception.CatolicoException
import rz.com.catolico.R
import rz.com.catolico.bean.Usuario
import rz.com.catolico.interfaces.Usuario.Login
import rz.com.catolico.utils.Constantes.Companion.USER_KEY
import rz.com.catolico.utils.Encrypts
import rz.com.catolico.utils.ValidaCampos
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*

class LoginScreenActivity : AppCompatActivity(), Login {

    private var usuario: Usuario? = null


    override fun doLoginSucess(usuario: Usuario) {
        Hawk.put<Any>(USER_KEY, this.usuario)
        setResult(Activity.RESULT_OK, Intent().putExtra(USER_KEY, usuario))
        finish()
    }


    private var callBackManager: CallbackManager? = null;
    private var TAG = " LOGIN SCREEN "
    private var validador = ValidaCampos();

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callBackManager?.onActivityResult(requestCode, resultCode, data)
    }

    fun printKeyHash(context: Activity): String? {
        val packageInfo: PackageInfo
        var key: String? = null
        try {
            //getting application package name, as defined in manifest
            val packageName = context.applicationContext.packageName

            //Retriving package info
            packageInfo = context.packageManager.getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES)

            Log.e("Package Name=", context.applicationContext.packageName)

            for (signature in packageInfo.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                key = String(Base64.encode(md.digest(), 0))

                // String key = new String(Base64.encodeBytes(md.digest()));
                Log.e("Key Hash=", key)
            }
        } catch (e1: PackageManager.NameNotFoundException) {
            Log.e("Name not found", e1.toString())
        } catch (e: NoSuchAlgorithmException) {
            Log.e("No such an algorithm", e.toString())
        } catch (e: Exception) {
            Log.e("Exception", e.toString())
        }

        return key
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_screen)
        Hawk.init(applicationContext).build()
        callBackManager = CallbackManager.Factory.create()
        //printKeyHash(this)

        btn_login_user.setOnClickListener {
            usuario = Usuario();
            if (validador.isValidEmailAddress(edt_user_email) && validador.isValidPassword(edt_password)) {
                usuario?.email = edt_user_email.text.toString()
                usuario?.password = Encrypts.criptografar(edt_password.text.toString(), Encrypts.CHAVE)
                try {
                   LoginTask(this@LoginScreenActivity, usuario!!, true).execute()
                } catch (exception: CatolicoException) {
                    exception.printStackTrace()
                }
            }
        }

        btn_login_facebook.setReadPermissions(Arrays.asList("public_profile", "email"))
        btn_login_facebook.registerCallback(callBackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                Log.d("User ID: ", loginResult.getAccessToken().getUserId() + "\n" + "Auth Token: " + loginResult.getAccessToken().getToken());
                val request = GraphRequest.newMeRequest(loginResult.accessToken) { `object`, response ->
                    Log.v("LoginActivity Response ", response.toString())
                    try {
                        Log.d("Facebook SDK", "Sucesso!!!")
                        usuario = Usuario()
                        usuario?.email = `object`.getString("email")
                        usuario?.nome = `object`.getString("name")
                        usuario?.idFacebook = `object`.getString("id")
                        LoginTask(this@LoginScreenActivity, usuario!!, true).execute()
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
                val parameters = Bundle()
                parameters.putString("fields", "id,name,email")
                request.parameters = parameters
                request.executeAsync()
            }

            override fun onCancel() {
                Log.d("erro", "usuario cancelou o login")
            }

            override fun onError(e: FacebookException) {
                Log.d("erro", e.message)
            }


        })

        txt_novo_usuario.setOnClickListener {
            startActivity(Intent(this, InsertEditUserActivity::class.java))
        }

    }

}