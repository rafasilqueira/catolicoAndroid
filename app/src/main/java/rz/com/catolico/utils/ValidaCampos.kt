package rz.com.catolico.utils

import android.support.design.widget.TextInputEditText

class ValidaCampos {

    fun isValidName(editText: TextInputEditText): Boolean {
        val s = editText.text!!.toString()
        if (s != "" && s.length > 5 && !Character.isWhitespace(s[0])) {
            return true
        } else
            editText.requestFocus()
        if (s == "") {
            editText.error = "Campo Nome deve ser preenchido."
        } else if (s.length < 5) {
            editText.error = "São necessários no minimo 05 caracteres."
        } else {
            editText.error = "Nome inválido"
        }
        return false
    }

    fun isValidPassword(editText: TextInputEditText): Boolean {
        val s = editText.text!!.toString()
        if (s != "" && s.length >= 6 && !s.contains(" ") && !Character.isWhitespace(s[0])) {
            return true
        } else
            editText.requestFocus()
        if (s == "") {
            editText.error = "Campo senha deve ser preenchido."
        } else if (s.length < 6) {
            editText.error = "São necessários no minimo 06 caracteres."
        } else if (s.contains(" ") || Character.isWhitespace(s[0])) {
            editText.error = "senha inválida"
        }
        return false
    }

    fun isValidNewPasswordNewUser(vararg params: Any): Boolean {
        val edtNewPassword = params[0] as TextInputEditText
        val edtConfirmationPassword = params[1] as TextInputEditText
        val newPassword = edtNewPassword.text!!.toString()
        val confirmationPassword = edtConfirmationPassword.text!!.toString()
        if (isValidPassword(edtNewPassword) && isValidPassword(edtConfirmationPassword)) {
            if (newPassword == confirmationPassword) {
                return true
            } else {
                edtNewPassword.requestFocus()
                edtNewPassword.error = "A senhas não são iguais"
                edtConfirmationPassword.setText("")
                edtNewPassword.setText("")
                return false
            }
        } else {
            return false
        }
    }

    fun changePassword(vararg params: Any): Boolean {
        val edtOldPassword = params[0] as TextInputEditText
        val edtNewPassword = params[1] as TextInputEditText
        val edtConfirmationPassword = params[2] as TextInputEditText
        val newPassword = edtNewPassword.text!!.toString()
        val confirmation = edtConfirmationPassword.text!!.toString()
        if (isValidPassword(edtOldPassword) && isValidPassword(edtNewPassword) && isValidPassword(edtConfirmationPassword)) {
            if (newPassword == confirmation) {
                return true
            } else {
                edtNewPassword.requestFocus()
                edtNewPassword.error = "As senhas não são iguais"
                edtNewPassword.animate()
                edtOldPassword.animate()
                edtConfirmationPassword.animate()
                edtOldPassword.setText("")
                edtNewPassword.setText("")
                edtConfirmationPassword.setText("")
                return false
            }
        } else {
            return false
        }
    }

    fun isValidEmailAddress(editText: TextInputEditText): Boolean {
        val s = editText.text!!.toString()
        val ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$"
        val p = java.util.regex.Pattern.compile(ePattern)
        val m = p.matcher(s)
        if (!m.matches()) {
            editText.requestFocus()
            if (s == "") {
                editText.error = "Campo E-mail e obrigatório"
            } else {
                editText.error = "E-mail inválido"
            }
        }
        return m.matches()
    }
}