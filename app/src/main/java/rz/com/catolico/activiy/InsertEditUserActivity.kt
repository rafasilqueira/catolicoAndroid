package rz.com.catolico.activiy

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.InputFilter
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_user_insert_edit.*
import rz.com.catolico.R
import rz.com.catolico.bean.Usuario
import rz.com.catolico.utils.Constantes.Companion.USER_KEY

class InsertEditUserActivity : AppCompatActivity() {

    private var usuario: Usuario? = null

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


    private fun setupUserStatus() {

        if (usuario != null) {

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

        } else {
            textInputLayoutOldPassword.visibility = View.GONE
            edtOldPassword.visibility = View.GONE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_insert_edit)
        usuario = (intent.getSerializableExtra(USER_KEY) as? Usuario)
        setupToolbar()
        setFilters()
        setupUserStatus()

    }
}