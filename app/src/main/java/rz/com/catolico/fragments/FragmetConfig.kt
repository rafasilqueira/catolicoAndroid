@file:Suppress("DEPRECATION")

package rz.com.catolico.fragments

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.Preference
import android.preference.PreferenceFragment
import android.preference.PreferenceGroup
import android.preference.PreferenceManager
import rz.com.catolico.dialogs.DialogUserDelete
import rz.com.catolico.R
import rz.com.catolico.bean.Usuario
import rz.com.catolico.activiy.AcitivityInsertEditUser
import rz.com.catolico.activiy.AcitivitySettings
import rz.com.catolico.enumeration.ActivitiesEnum
import rz.com.catolico.utils.Constantes.Companion.USER_KEY

class FragmetConfig : PreferenceFragment(), SharedPreferences.OnSharedPreferenceChangeListener {

    private var myActivity: Activity? = null
    private var usuario: Usuario? = null
    private var userDataPreference: Preference? = null
    private var userPasswordPreference: Preference? = null
    private var userDeletePreference: Preference? = null
    private var dialogDeleteUserDelete: DialogUserDelete? = null


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(resultCode){
            RESULT_OK ->{
                this.usuario = (data?.getSerializableExtra(USER_KEY) as Usuario)
                updateUser(usuario!!)
            }
        }
    }

    fun updateUser(usuario: Usuario){
        getParent(userDataPreference!!)?.title = usuario?.name
        (myActivity as AcitivitySettings).setUsuario(usuario!!)
    }


    companion object {
        fun createInstance(usuario: Usuario): FragmetConfig {
            val configFragment = FragmetConfig()
            val bundle = Bundle()
            bundle.putSerializable(USER_KEY, usuario)
            configFragment.arguments = bundle
            return configFragment
        }
    }

    private fun getParent(preference: Preference): PreferenceGroup? {
        return getParent(preferenceScreen, preference)
    }

    private fun getParent(root: PreferenceGroup, preference: Preference): PreferenceGroup? {
        for (i in 0 until root.preferenceCount) {
            val p = root.getPreference(i)
            if (p === preference)
                return root
            if (PreferenceGroup::class.java.isInstance(p)) {
                val parent = getParent(p as PreferenceGroup, preference)
                if (parent != null)
                    return parent
            }
        }
        return null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.general_settings)
        myActivity = activity
        usuario = arguments.getSerializable(USER_KEY) as Usuario
        userDataPreference = findPreference("user_informations")
        userPasswordPreference = findPreference("user_password")
        userDeletePreference = findPreference("user_delete")

        if (usuario?.idFacebook != null) {
            userPasswordPreference?.isEnabled = false
        }

        userDataPreference?.setOnPreferenceClickListener {
            startActivityForResult(
                    Intent(
                            activity,
                            AcitivityInsertEditUser::class.java).putExtra(USER_KEY, usuario),
                    ActivitiesEnum.INSERT_EDIT_USER.code
            )
            true
        }

        userPasswordPreference?.setOnPreferenceClickListener {
            startActivityForResult(
                    Intent(
                            activity,
                            AcitivityInsertEditUser::class.java)
                            .putExtra(USER_KEY, usuario)
                            .putExtra("passwordChange", true),
                    ActivitiesEnum.INSERT_EDIT_USER.code
            )
            true
        }

        userDeletePreference?.setOnPreferenceClickListener {
            dialogDeleteUserDelete = DialogUserDelete.getInstance(usuario!!)
            dialogDeleteUserDelete!!.show((activity as AcitivitySettings).supportFragmentManager, "")
            true
        }

        if (usuario != null) {
            updateUser(usuario!!)
        }
        val prefs = PreferenceManager.getDefaultSharedPreferences(activity)
        prefs.registerOnSharedPreferenceChangeListener(this)

    }


    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
    }
}