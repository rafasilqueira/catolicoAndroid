package rz.com.catolico.activiy

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.view.Menu
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_selected_santo.*
import rz.com.catolico.R
import rz.com.catolico.adapter.AdapterAbstractViewPager
import rz.com.catolico.bean.Santo
import rz.com.catolico.exception.CatolicoException
import rz.com.catolico.fragments.FragmentSantoHistory
import rz.com.catolico.fragments.FragmentSantoRelated
import rz.com.catolico.interfaces.IFavoriteSanto
import rz.com.catolico.interfaces.ISelectable
import rz.com.catolico.utils.SantoUtils

class ActivitySelectedSanto : ActivityAbstract(), IFavoriteSanto, ISelectable {


    private fun setupToolbar() {
        setSupportActionBar(mtoolbar)
        mtoolbar.setTitleTextColor(resources.getColor(android.R.color.white))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search_filter_favorite, menu)
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selected_santo)
        setupToolbar()
        (getIntentObject("santo") as Santo?)?.let {
            setActionBarTitle(SantoUtils.formatterComemoracao.format(it.comemoracao))
            setupViewPager(it)
            if (it.imgurl != "") Picasso.with(this).load(it.imgurl).into(imgSanto)
            return
        }

        throw CatolicoException("param santo is invalid")

    }


    private fun setupViewPager(santo: Santo) {
        val viewPagerAdapter = AdapterAbstractViewPager(supportFragmentManager)
        tabLayout?.tabMode = TabLayout.MODE_FIXED
        viewPagerAdapter.addFrag(FragmentSantoHistory.instance(santo), getString(R.string.historia))
        viewPagerAdapter.addFrag(FragmentSantoRelated.instance(santo), getString(R.string.related))
        tabLayout?.setupWithViewPager(viewPager)
        viewPager?.adapter = viewPagerAdapter
    }

    override fun onFavoriteListener() {
        /*getUser()?.let { onUpdateFavorite(santo, it) }*/
    }

    override fun onShareListener() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSucessUpdateFavorite(type: Santo) {
        /*getParentActivity().isFavorite(type.favorite)*/
    }


}