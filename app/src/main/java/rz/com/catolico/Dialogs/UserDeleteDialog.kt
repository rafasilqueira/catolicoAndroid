package rz.com.catolico.Dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import rz.com.catolico.R
import rz.com.catolico.bean.Usuario
import rz.com.catolico.utils.Constantes.Companion.USER_KEY

class UserDeleteDialog : DialogFragment() {


    var usuario: Usuario? = null

    companion object {
        fun getInstance(usuario: Usuario): UserDeleteDialog {
            var userDeleteConfirmation = UserDeleteDialog()
            var bundle = Bundle()
            bundle.putSerializable(USER_KEY, usuario)
            userDeleteConfirmation.arguments = bundle
            return userDeleteConfirmation
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        usuario = arguments?.getSerializable(USER_KEY) as Usuario
        return AlertDialog.Builder(activity)
                .setTitle(getString(R.string.meus_dados))
                .setMessage(getString(R.string.delete_account_text))
                .setPositiveButton("YES") { dialog, which ->
                    dialog.dismiss()
                }
                .setNegativeButton(android.R.string.no){dialog, which ->
                    dialog.dismiss()
                }
                .create()
        //return super.onCreateDialog(savedInstanceState)

    }
}