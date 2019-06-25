package rz.com.catolico.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import rz.com.catolico.R
import rz.com.catolico.activiy.ActivityBaseFragment
import rz.com.catolico.bean.Usuario

@Suppress("UNCHECKED_CAST")
abstract class FragmentAbstract<A : ActivityBaseFragment>(val initialView: Int) : Fragment() {

    protected var parentActivity: A? = null
    protected var view: ViewGroup? = null
    protected var mInflater: LayoutInflater? = null
    protected var mContainer: ViewGroup? = null
    protected var usuario: Usuario? = null

    abstract override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?

    fun disableAllIcons() {
        parentActivity?.disableAllIcons()
    }

    fun swapFragment(fragment: Fragment, TAG: String) {
        fragmentManager!!.beginTransaction()
                .hide(this)
                .add(R.id.frame_layout, fragment, TAG)
                .addToBackStack(null)
                .commit()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        parentActivity = context as A
        parentActivity?.actionAttachFragment(this@FragmentAbstract)
        usuario = parentActivity?.getIntentUser()
        parentActivity?.let { it.supportActionBar?.title = getString(R.string.app_name) }
        println("abstract")
    }

    fun changeView(layout: Int) {
        val newView = mInflater?.inflate(layout, mContainer, false)
        view?.removeAllViews()
        view?.addView(newView)
    }


}