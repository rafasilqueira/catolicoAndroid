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
abstract class FragmentAbstract<A : ActivityBaseFragment> : Fragment() {

    /*protected var view: ViewGroup? = null
    protected var mInflater: LayoutInflater? = null
    protected var mContainer: ViewGroup? = null*/

    abstract override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    open fun actionAfterAttachFragment() {}

    protected fun getParentActivity() = activity as A

    protected fun getUser(): Usuario? = getParentActivity().getIntentUser()

    fun disableAllIcons() {
        getParentActivity().disableAllIcons()
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
        actionAfterAttachFragment()
    }


    fun changeView(layout: Int) {
        val mContainer = (view?.parent as ViewGroup)
        val view = view as ViewGroup
        val newView = layoutInflater?.inflate(layout, mContainer, false)
        view.removeAllViews()
        view.addView(newView)
    }


}