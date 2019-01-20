@file:Suppress("DEPRECATION")

package rz.com.catolico.activiy

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceFragment
import rz.com.catolico.R
import rz.com.catolico.bean.Usuario
import rz.com.catolico.utils.Constantes.Companion.USER_KEY

class ConfigFragmet : PreferenceFragment(), SharedPreferences.OnSharedPreferenceChangeListener {

    companion object {
        fun createInstance(usuario: Usuario): ConfigFragmet {
            val configFragment = ConfigFragmet()
            val bundle = Bundle()
            bundle.putSerializable(USER_KEY, usuario)
            configFragment.arguments = bundle
            return configFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.general_settings)
    }


    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}