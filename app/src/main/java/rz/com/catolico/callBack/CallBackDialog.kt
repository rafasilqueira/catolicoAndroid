package rz.com.catolico.callBack

import android.app.Dialog

import android.content.Context
import retrofit2.Call
import retrofit2.Response
import android.app.ProgressDialog
import retrofit2.Callback
import rz.com.catolico.R


open class CallBackDialog<T>(context: Context) : Callback<T> {


    override fun onResponse(call: Call<T>, response: Response<T>) { dissmissDialog() }

    override fun onFailure(call: Call<T>, t: Throwable) { dissmissDialog() }

    var dialog: Dialog? = null
    var context : Context? =null

    init {
        this.context = context
        dialog = ProgressDialog.show(context, context.getString(R.string.aguarde), context.getString(R.string.dialog_wait_pt_br))
    }

    fun dissmissDialog(){ if (dialog?.isShowing!!) dialog?.dismiss() }

}