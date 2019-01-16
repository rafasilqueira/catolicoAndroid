package rz.com.catolico.activiy

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_insert_edit)
        usuario = (intent.getSerializableExtra(USER_KEY) as? Usuario)
        setupToolbar()

    }
}