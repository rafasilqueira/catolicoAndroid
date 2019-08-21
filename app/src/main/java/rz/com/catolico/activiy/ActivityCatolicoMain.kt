package rz.com.catolico.activiy

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.facebook.Profile
import com.facebook.login.LoginManager
import com.orhanobut.hawk.Hawk
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_catolico_main.*
import rz.com.catolico.R
import rz.com.catolico.bean.Usuario
import rz.com.catolico.enumeration.FeatureCode
import rz.com.catolico.fragments.FragmentLiturgia
import rz.com.catolico.fragments.FragmentOracao
import rz.com.catolico.fragments.FragmentSanto
import rz.com.catolico.interfaces.IFiltered
import rz.com.catolico.interfaces.IUpdatableFragment
import rz.com.catolico.utils.Constantes.Companion.LITURGIA_FRAGMENT_TAG
import rz.com.catolico.utils.Constantes.Companion.ORACAO_FRAGMENT_TAG
import rz.com.catolico.utils.Constantes.Companion.SANTO_FRAGMENT_TAG
import rz.com.catolico.utils.Constantes.Companion.USER_KEY
import rz.com.catolico.utils.StatusFacebookLogin

class ActivityCatolicoMain : ActivityBaseFragment(), OnNavigationItemSelectedListener {

    private var usuario: Usuario? = null
    private var header: View? = null
    private var menu: Menu? = null
    private var menuItemMeusDadosDV: MenuItem? = null
    private var menuItemMinhasOracoesDV: MenuItem? = null
    private var menuItemSugestaoDV: MenuItem? = null
    private var menuItemAutenticateDV: MenuItem? = null
    private var linearLayoutHeader: LinearLayout? = null
    private var imgUser: CircleImageView? = null
    private var txtUserName: TextView? = null
    private var txtUserEmail: TextView? = null
    private var menuItemFavoritar: MenuItem? = null
    private var menuItemShare: MenuItem? = null
    private var menuItemFilter: MenuItem? = null
    private var menuItemSearch: MenuItem? = null
    private var doubleBackToExitPressedOnce: Boolean = false
    private var selectedFragment: Fragment? = null
    var fragmentSantoSavedInstance: FragmentSanto? = null
    var fragmentOracaoSavedInstance: FragmentOracao? = null
    var fragmentLiturgiaSavedInstace: FragmentLiturgia? = null

    private fun setupMenuItemDV() {
        if (usuario != null) {
            menuItemMeusDadosDV?.isVisible = true
            menuItemMinhasOracoesDV?.isVisible = true
            menuItemSugestaoDV?.isVisible = false
            menuItemAutenticateDV?.setTitle(R.string.sair)
            setupIconAutenticate(true)
            linearLayoutHeader?.visibility = View.VISIBLE
            txtUserName?.text = usuario?.name
            txtUserEmail?.text = usuario?.email
            if (StatusFacebookLogin.isFacebookLoggedIn(this@ActivityCatolicoMain)) {
                val profile = Profile.getCurrentProfile()
                Picasso.with(this@ActivityCatolicoMain)
                        .load(profile?.getProfilePictureUri(200, 200).toString())
                        .placeholder(R.drawable.ic_account_circle_white_96dp)
                        .error(R.drawable.ic_account_circle_white_96dp)
                        .into(imgUser)
            }
        } else {
            menuItemMeusDadosDV?.isVisible = false
            menuItemMinhasOracoesDV?.isVisible = false
            menuItemSugestaoDV?.isVisible = false
            menuItemAutenticateDV?.setTitle(R.string.entrar)
            setupIconAutenticate(false)
            linearLayoutHeader?.visibility = View.GONE
            txtUserName?.text = getString(R.string.user)
            txtUserEmail?.text = ""
            imgUser?.setImageResource(R.drawable.ic_account_circle_white_96dp)
        }
    }


    private fun setupIconAutenticate(isUsuarioLoged: Boolean) {
        val screenLayout = resources.configuration.screenLayout
        if (isUsuarioLoged) {
            if (screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK == Configuration.SCREENLAYOUT_SIZE_NORMAL || screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK == Configuration.SCREENLAYOUT_SIZE_SMALL) {
                menuItemAutenticateDV?.setIcon(R.drawable.ic_arrow_back_black_36dp)
            } else if (screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK == Configuration.SCREENLAYOUT_SIZE_LARGE) {
                menuItemAutenticateDV?.setIcon(R.drawable.ic_arrow_back_black_96dp)
            } else if (screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK == Configuration.SCREENLAYOUT_SIZE_XLARGE) {
                menuItemAutenticateDV?.setIcon(R.drawable.ic_arrow_back_black_144dp)
            }
        } else {
            if (screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK == Configuration.SCREENLAYOUT_SIZE_NORMAL || screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK == Configuration.SCREENLAYOUT_SIZE_SMALL) {
                menuItemAutenticateDV?.setIcon(R.drawable.ic_right_arrow_black_36dp)
            } else if (screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK == Configuration.SCREENLAYOUT_SIZE_LARGE) {
                menuItemAutenticateDV?.setIcon(R.drawable.ic_right_arrow_black_96dp)
            } else if (screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK == Configuration.SCREENLAYOUT_SIZE_XLARGE) {
                menuItemAutenticateDV?.setIcon(R.drawable.ic_right_arrow_black_144dp)
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.menu_item_user_profile -> startActivityForResult(Intent(this, AcitivitySettings::class.java).putExtra(USER_KEY, usuario), FeatureCode.SETTINGS.code)

            R.id.menu_item_oracoes_favoritas -> startActivityForResult(Intent(this, ActivityMinhasOracoes::class.java), FeatureCode.MINHAS_ORACOES.code)

            R.id.menu_item_compartilhe -> {
            }

            R.id.menu_item_rate_us -> {
            }

            R.id.menu_item_about -> {
            }

            R.id.menu_item_autenticate -> {
                if (usuario != null) {
                    endSession()
                } else {
                    startActivityForResult(Intent(this, AcitivityILoginScreen::class.java), FeatureCode.LOGIN_SCREEN.code)
                }
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val fragment = getCurrentFragment()
        when (item?.itemId) {
           /* R.id.ic_favorite -> if (fragment is ISelectable) fragment.onFavoriteListener()
            R.id.ic_share -> if (fragment is ISelectable) fragment.onShareListener()*/
            R.id.ic_order_by_category -> if (fragment is IFiltered) fragment.categoryFilterListener()
            R.id.ic_order_alfabetical -> if (fragment is IFiltered) fragment.alphabeticalFilterListener()

            R.id.ic_search -> {
                when (fragment) {
                    is FragmentSanto -> {
                        fragment.showDialogDatePicker()
                    }

                    is FragmentOracao -> {
                        println("ToDo")
                    }
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun endSession() {
        if (StatusFacebookLogin.isFacebookLoggedIn(this)) {
            LoginManager.getInstance().logOut()
        }
        Hawk.delete(USER_KEY)
        clearAllFlagsAndRestart(true)
    }

    private fun clearAllFlagsAndRestart(openDrawer: Boolean) {
        val intent = Intent(this, AcitivitySplashScreen::class.java)
        intent.putExtra("finish", true).flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        if (openDrawer) intent.putExtra("drawer", true)
        startActivity(intent)
        finish()
    }

    fun setupFragmentIcons(fragment: Fragment) {
        disableAllIcons()
        setActionBarTitle(getString(R.string.app_name))
        when (fragment) {
            is FragmentSanto -> showIconsFragmentSanto()
            is FragmentOracao -> showIconsFragmentOracao()
            is FragmentLiturgia -> {
            }
        }
    }

    fun isFavorite(favorite: Boolean) {
        menuItemFavoritar?.let {
            if (favorite) {
                it.setIcon(R.drawable.ic_favorite_star_selected_action_bar)
            } else {
                it.setIcon(R.drawable.ic_favorite_star_unselected_action_bar)
            }
        }
    }

    fun showIconsSelectedContent() {
        menuItemShare?.isVisible = true
        if (getUser() != null) {
            menuItemFavoritar?.isVisible = true
        }
    }

    private fun showIconsFragmentSanto() {
        menuItemSearch?.isVisible = true
    }

    private fun showIconsFragmentOracao() {
        menuItemSearch?.isVisible = true
        menuItemFilter?.isVisible = true
    }

    override fun disableAllIcons() {
        menuItemFavoritar?.isVisible = false
        menuItemShare?.isVisible = false
        menuItemFilter?.isVisible = false
        menuItemSearch?.isVisible = false
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuItemFavoritar = menu?.findItem(R.id.ic_favorite)
        menuItemShare = menu?.findItem(R.id.ic_share)
        menuItemFilter = menu?.findItem(R.id.ic_options_filter)
        menuItemSearch = menu?.findItem(R.id.ic_search)
        selectedFragment = FragmentSanto.instance()
        replaceFragment(selectedFragment!!, SANTO_FRAGMENT_TAG)
        return true
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            if (isBackStackEmpty()) {

                val pressedBackFragment = getCurrentFragment()
                val topFragment = getTopFragment()

                topFragment?.let { setupFragmentIcons(it) }

                when (pressedBackFragment) {
                    is IUpdatableFragment -> pressedBackFragment.update()
                }

                if (doubleBackToExitPressedOnce) {
                    super.onBackPressed()
                }

                showToastPressBackAgain()
            } else {
                super.onBackPressed()
            }
        }
    }

    private fun showToastPressBackAgain() {
        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, getString(R.string.press_back_again_to_exit), Toast.LENGTH_SHORT).show()
        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 1500)
    }

    private fun getIntentUser(data: Intent?): Usuario {
        return (data?.getSerializableExtra(USER_KEY) as Usuario)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            RESULT_OK -> {
                when (requestCode) {
                    FeatureCode.LOGIN_SCREEN.code -> {
                        if (data?.getSerializableExtra(USER_KEY) != null) {
                            clearAllFlagsAndRestart(true)
                        }
                    }

                    FeatureCode.SETTINGS.code -> {
                        this.usuario = getIntentUser(data)
                        setupMenuItemDV()
                    }
                }
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catolico_main)
        Hawk.init(applicationContext).build()
        usuario = getUser()
        setupToolbar()
        setupDrawerLayout()
        setupMenuItemDV()

        bottom_navigation.setOnNavigationItemSelectedListener { menuItem ->
            disableAllIcons()
            var tag: String? = null
            when (menuItem.itemId) {
                R.id.action_load_saynts -> {
                    tag = SANTO_FRAGMENT_TAG
                    selectedFragment = if (fragmentSantoSavedInstance != null) fragmentSantoSavedInstance else FragmentSanto.instance()
                }

                R.id.action_load_liturgia -> {
                    tag = LITURGIA_FRAGMENT_TAG
                    selectedFragment = if (fragmentLiturgiaSavedInstace != null) fragmentLiturgiaSavedInstace else FragmentLiturgia.instance()
                }

                R.id.action_load_prays -> {
                    tag = ORACAO_FRAGMENT_TAG
                    selectedFragment = if (fragmentOracaoSavedInstance != null) fragmentOracaoSavedInstance else FragmentOracao.instance()
                }
            }

            removeAllFragments()

            selectedFragment?.let {
                replaceFragment(it, tag)
            }
            true
        }

    }

    private fun setupDrawerLayout() {
        val toggle = ActionBarDrawerToggle(this, drawer_layout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        navigation_view.setNavigationItemSelectedListener(this)
        header = navigation_view.getHeaderView(0)
        menu = navigation_view.menu
        linearLayoutHeader = header?.findViewById<View>(R.id.linear_layout_header) as LinearLayout
        txtUserName = header?.findViewById<View>(R.id.header_user_name) as TextView
        txtUserEmail = header?.findViewById<View>(R.id.header_user_email) as TextView
        imgUser = header?.findViewById<View>(R.id.user_icon) as CircleImageView
        menuItemMeusDadosDV = menu?.findItem(R.id.menu_item_user_profile)
        menuItemMinhasOracoesDV = menu?.findItem(R.id.menu_item_oracoes_favoritas)
        menuItemSugestaoDV = menu?.findItem(R.id.menu_item_sugerir_oracao)
        menuItemAutenticateDV = menu?.findItem(R.id.menu_item_autenticate)
        if (getUser() != null && intent.getBooleanExtra("drawer", false)) {
            drawer_layout.openDrawer(GravityCompat.START)
        }
    }


    private fun setupToolbar() {
        setSupportActionBar(mToolbar)
        mToolbar.setTitleTextColor(ContextCompat.getColor(this, android.R.color.white))
        mToolbar.title = "Católico de Fé"
    }

}