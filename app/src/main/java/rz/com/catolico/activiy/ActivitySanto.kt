package rz.com.catolico.activiy

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import br.com.tupinamba.model.bean.Santo
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_santo.*
import rz.com.catolico.R
import rz.com.catolico.utils.SantoUtils.Companion.formatterComemoracao
import rz.com.catolico.utils.SantoUtils.Companion.getDaysToDate

class ActivitySanto : AppCompatActivity() {

    private var santo: Santo? = null

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
        mtoolbar.setTitleTextColor(resources.getColor(android.R.color.white))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        santo = intent.getSerializableExtra("santo") as Santo
        setContentView(R.layout.activity_santo)
        setupToolbar()
        Picasso.with(this@ActivitySanto)
                .load(santo?.imgurl)
                .placeholder(R.drawable.ic_santo)
                .error(R.drawable.ic_santo)
                .into(imgSaynt)

        title = santo?.nome
        txtSantoNome.text = santo?.nome
        txtDiasData.text = getDaysToDate(this, santo!!.diasData)
        txtSantoComemoracao.text = formatterComemoracao.format(santo?.comemoracao)
        txtSantoDescription.text = santo?.descricao
    }
}