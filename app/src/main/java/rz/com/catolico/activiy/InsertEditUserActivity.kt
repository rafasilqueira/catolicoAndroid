package rz.com.catolico.activiy

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.InputFilter
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.google.gson.GsonBuilder
import com.orhanobut.hawk.Hawk
import kotlinx.android.synthetic.main.activity_user_insert_edit.*
import retrofit2.Call
import retrofit2.Response
import rz.com.catolico.R
import rz.com.catolico.bean.CallBackDialog
import rz.com.catolico.bean.Usuario
import rz.com.catolico.retrofit.RetrofitConfig
import rz.com.catolico.utils.Constantes.Companion.USER_KEY
import rz.com.catolico.utils.Encrypts
import rz.com.catolico.utils.ToastMisc
import rz.com.catolico.utils.ValidaCampos.Companion.changePasswordValidator
import rz.com.catolico.utils.ValidaCampos.Companion.isValidEmailAddress
import rz.com.catolico.utils.ValidaCampos.Companion.isValidName
import rz.com.catolico.utils.ValidaCampos.Companion.isValidNewPasswordNewUser

class InsertEditUserActivity : AppCompatActivity() {

    private var usuario: Usuario? = null
    private var changePassword = false

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                setResult(Activity.RESULT_CANCELED)
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun setupToolbar() {
        setSupportActionBar(mtoolbar)
        title = getString(R.string.meus_dados)
        mtoolbar.setTitleTextColor(resources.getColor(android.R.color.white))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)
    }

    fun setFilters() {
        edtNome.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(40))
        edtPassword.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(15))
        edtPasswordConfirm.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(15))
        edtPassword.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(15))
        edtOldPassword.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(15))
    }

    private fun setupChangePasswordView() {
        edtNome.setText(usuario?.nome)
        edtEmail.setText(usuario?.email)
        textInputLayoutPassword.visibility = View.VISIBLE
        edtPassword.visibility = View.VISIBLE

        edtPasswordConfirm.visibility = View.VISIBLE
        textInputLayoutConfirm.visibility = View.VISIBLE

        textInputLayoutOldPassword.visibility = View.VISIBLE
        edtOldPassword.visibility = View.VISIBLE

        edtNome.visibility = View.GONE
        edtEmail.visibility = View.GONE
    }

    private fun setupEditUserView() {
        edtNome.setText(usuario?.nome)
        edtEmail.setText(usuario?.email)
        edtEmail.isEnabled = false
        edtEmail.setTextColor(resources.getColor(R.color.red))
        textInputLayoutPassword.visibility = View.GONE
        edtPassword.visibility = View.GONE
        edtPasswordConfirm.visibility = View.GONE
        textInputLayoutConfirm.visibility = View.GONE
        textInputLayoutOldPassword.visibility = View.GONE
        edtOldPassword.visibility = View.GONE
    }

    private fun setupUserStatus() {
        if (usuario != null) {
            if (changePassword) {
                setupChangePasswordView()
            } else {
                setupEditUserView()
            }
        } else {
            textInputLayoutOldPassword.visibility = View.GONE
            edtOldPassword.visibility = View.GONE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_insert_edit)
        Hawk.init(applicationContext).build()
        usuario = (intent.getSerializableExtra(USER_KEY) as? Usuario)
        changePassword = intent.getBooleanExtra("passwordChange", false)
        TextView(this@InsertEditUserActivity)
        setupToolbar()
        setFilters()
        setupUserStatus()


        btnFinalizar.setOnClickListener {
            if (isValidName(edtNome) && isValidEmailAddress(edtEmail)) {
                var nome = edtNome.text!!.toString()
                var email = edtEmail.text!!.toString()
                if (usuario == null) {
                    if (isValidNewPasswordNewUser(edtPassword, edtPasswordConfirm)) {
                        usuario = Usuario()
                        try {
                            usuario?.password = encryptPassword(edtPassword.text.toString())
                        } catch (e: Exception) {
                            e.printStackTrace()
                            return@setOnClickListener
                        }
                    }
                } else {
                    if (changePassword) {
                        var storagePassword = decrypt((Hawk.get(USER_KEY) as Usuario)?.password!!)
                        println(storagePassword)
                        if (changePasswordValidator(storagePassword, edtOldPassword, edtPassword, edtPasswordConfirm))
                            usuario?.password = encryptPassword(edtPassword.text.toString())
                        else
                            return@setOnClickListener
                    }
                }
                usuario?.nome = nome
                usuario?.email = email
                println(GsonBuilder().setPrettyPrinting().create().toJson(usuario))
                saveUpdateUser(usuario!!)
            }
        }
    }

    private fun encryptPassword(password: String): String {
        return Encrypts.criptografar(password, Encrypts.CHAVE).trim()
    }

    private fun decrypt(password: String): String {
        return Encrypts.descriptografar(password, Encrypts.CHAVE)
    }


    private fun saveUpdateUser(usuario: Usuario) {

        val call: Call<Boolean> = if (!changePassword)
            RetrofitConfig().usuarioService().saveUser(usuario)
        else
            RetrofitConfig().usuarioService().changePassword(usuario)

        call.enqueue(object : CallBackDialog<Boolean>(this@InsertEditUserActivity) {

            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                super.onResponse(call, response)
                ToastMisc.sucess(this@InsertEditUserActivity)
                Hawk.put<Any>(USER_KEY, usuario)
                setResult(Activity.RESULT_OK, Intent().putExtra(USER_KEY, usuario))
                finish()
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                super.onFailure(call, t)
                ToastMisc.generalError(this@InsertEditUserActivity)
            }
        })

    }

}