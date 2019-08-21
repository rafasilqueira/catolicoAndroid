package rz.com.catolico.activiy

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.orhanobut.hawk.Hawk
import kotlinx.android.synthetic.main.activity_oracoes_favoritas.*
import rz.com.catolico.R
import rz.com.catolico.adapter.AdapterOracao
import rz.com.catolico.adapter.AdapterOracaoCategory
import rz.com.catolico.bean.Oracao
import rz.com.catolico.enumeration.FeatureCode.ACTIVITY_SELECTED_ORACAO
import rz.com.catolico.interfaces.IAdapter
import rz.com.catolico.interfaces.IFiltered
import rz.com.catolico.interfaces.ISortOracao

class ActivityMinhasOracoes : ActivityAbstract(), IAdapter<Oracao>, ISortOracao, IFiltered {

    private var showByCategory = true
    var selectedAdapter: AdapterOracao? = null
    private lateinit var oracoes: MutableList<Oracao>

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        selectedAdapter?.let {
            it.syncronize()
            it.notifyDataSetChanged()
        }
    }

    private fun setupToolbar(title: String = getString(R.string.app_name)) {
        setSupportActionBar(mtoolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeButtonEnabled(true)
            setActionBarTitle(title)
        }
    }

    /*override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        setupToolbar()
        return true
    }*/

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_minhas_oracoes, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> onBackPressed()
            R.id.ic_order_alfabetical -> alphabeticalFilterListener()
            R.id.ic_order_by_category -> categoryFilterListener()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_oracoes_favoritas)
        Hawk.init(applicationContext).build()
        setupToolbar(getString(R.string.minhas_oracoes))
        loadData()
    }

    override fun setupAdapter(mList: MutableList<Oracao>) {
        recyclerview.layoutManager = getLinearLayoutManager(this, IAdapter.VERTICAL)
        val map = if (showByCategory) sortByCategory(mList) else sortAlphabeticalMap(mList)
        val adapter = AdapterOracaoCategory(this, map)
        recyclerview.adapter = adapter
    }

    override fun loadData() {
        getUser()?.let {
            oracoes = it.oracoes
            onSucessLoadData(oracoes)
        }
    }

    override fun onSucessLoadData(list: MutableList<Oracao>) {
        setupAdapter(list)
    }

    override fun onErrorLoadData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemClick(type: Oracao) {
        startActivityForResult(Intent(this, ActivitySelectedOracao::class.java).putExtra("oracao", type), ACTIVITY_SELECTED_ORACAO.code)
    }

    override fun alphabeticalFilterListener() {
        showByCategory = false
        setupAdapter(oracoes)
    }

    override fun categoryFilterListener() {
        showByCategory = true
        setupAdapter(oracoes)
    }
}
