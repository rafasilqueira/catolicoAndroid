package rz.com.catolico.activiy

import android.app.Activity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_selected_santo.*
import rz.com.catolico.R
import rz.com.catolico.exception.CatolicoException
import rz.com.catolico.interfaces.ISelectable

abstract class ActivitySelectable : ActivityAbstract(), ISelectable {

    private lateinit var icShare: MenuItem
    private lateinit var icFavorite: MenuItem

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                setResult(Activity.RESULT_CANCELED)
                onBackPressed()
            }
            R.id.ic_share -> onShareListener()
            R.id.ic_favorite -> onFavoriteListener()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search_filter_favorite, menu)

        icShare = menu?.findItem(R.id.ic_share)
                ?: throw CatolicoException("icon icShare is invalid")

        icFavorite = menu.findItem(R.id.ic_favorite)
                ?: throw CatolicoException("icon icFavorite is invalid")

        setupMenuBarIcons()
        return true
    }

    protected fun setupToolbar(title: String = getString(R.string.app_name)) {
        setSupportActionBar(mtoolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)
        setActionBarTitle(title)
    }

    override fun setupMenuBarIcons() {
        if (isUserLogged()) {
            icFavorite.isVisible = true
            icShare.isVisible = true
        }
    }

    fun isFavorite(favorite: Boolean) {
        val icon = if (favorite) R.drawable.ic_favorite_star_selected_action_bar else R.drawable.ic_favorite_star_unselected_action_bar
        icFavorite.setIcon(icon)
    }

}