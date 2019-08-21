package rz.com.catolico.activiy

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.view.Menu
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_selected_santo.*
import rz.com.catolico.R
import rz.com.catolico.adapter.AdapterAbstractViewPager
import rz.com.catolico.bean.Santo
import rz.com.catolico.bean.Usuario
import rz.com.catolico.exception.CatolicoException
import rz.com.catolico.fragments.FragmentSantoHistory
import rz.com.catolico.fragments.FragmentSantoRelated
import rz.com.catolico.interfaces.IFavoriteSanto
import rz.com.catolico.utils.SantoUtils

class ActivitySelectedSanto : ActivitySelectable(), IFavoriteSanto {

    private lateinit var santo: Santo

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        isFavorite(santo.favorite)
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selected_santo)

        santo = getIntentObject("santo") as Santo?
                ?: throw CatolicoException("param santo is invalid")


        setupToolbar(SantoUtils.formatterComemoracao.format(santo.comemoracao))
        setupViewPager(santo)
        if (santo.imgurl != "") Picasso.with(this).load(santo.imgurl).into(imgSanto)
        return

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
        getUser()?.let {
            onUpdateFavorite(santo, it)
        }
    }

    override fun onShareListener() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSucessUpdateFavorite(type: Santo, user: Usuario) {
        super.onSucessUpdateFavorite(type,user)
        isFavorite(santo.favorite)
    }

}