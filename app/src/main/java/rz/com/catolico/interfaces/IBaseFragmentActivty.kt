package rz.com.catolico.interfaces

import android.support.v4.app.Fragment
import rz.com.catolico.bean.Usuario

interface IBaseFragmentActivty {
    fun getIntentUser(): Usuario?
    fun disableAllIcons()
    fun actionAttachFragment(fragment: Fragment)
}