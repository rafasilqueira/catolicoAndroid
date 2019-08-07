package rz.com.catolico.activiy

import android.support.v4.app.Fragment
import rz.com.catolico.R

abstract class ActivityBaseFragment : ActivityAbstract() {

   abstract fun disableAllIcons()

    protected fun getTopFragment(): Fragment? {
        supportFragmentManager.run {
            return when (backStackEntryCount) {
                0 -> null
                else -> findFragmentByTag(getBackStackEntryAt(backStackEntryCount - 1).name)
            }
        }
    }

    protected fun replaceFragment(selectedFragment: Fragment, tag: String?) {
        supportFragmentManager.beginTransaction().replace(R.id.frame_layout, selectedFragment, tag).commit()
    }

    protected fun removeAllFragments() {
        val fm = supportFragmentManager
        for (i in 0 until fm.backStackEntryCount) fm.popBackStack()
    }

    private fun getBackStackEntryCount() = supportFragmentManager.backStackEntryCount
    protected fun isBackStackEmpty() = getBackStackEntryCount() == 0
    protected fun getCurrentFragment() = supportFragmentManager.findFragmentById(R.id.frame_layout)


}