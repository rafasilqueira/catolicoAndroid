package rz.com.catolico.interfaces

import rz.com.catolico.bean.Usuario

interface IBaseFragmentActivty {
    fun getIntentUser(): Usuario?
    fun disableAllIcons()
}