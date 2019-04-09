package rz.com.catolico.activiy

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.com.tupinamba.model.bean.Santo
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_santo.*
import rz.com.catolico.R

class ActivitySanto : AppCompatActivity() {

    private var santo : Santo? = null

    private fun setupToolbar() {
        setSupportActionBar(mtoolbar)
        mtoolbar.setTitleTextColor(resources.getColor(android.R.color.white))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_santo)
        setupToolbar()
        santo = intent.getSerializableExtra("santo") as Santo
        Picasso.with(this@ActivitySanto)
                .load(santo?.imgurl)
                .placeholder(R.drawable.ic_santo)
                .error(R.drawable.ic_santo)
                .into(img_saynt)

        title = santo?.nome

    }
}