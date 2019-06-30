package rz.com.catolico.callBack

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rz.com.catolico.R
import rz.com.catolico.fragments.FragmentAbstract


open class CallBackFragment<T>(
        private val fragment: FragmentAbstract<*>,
        private val onSucessView: Int,
        private val onLoadView: Int = R.layout.fragment_load_screen,
        private val onErrorView: Int = R.layout.fragment_erro_screen_top)
    : Callback<T> {

    override fun onResponse(call: Call<T>, response: Response<T>) {
        if (response.isSuccessful) {
            this.fragment.changeView(onSucessView)
        }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        t.printStackTrace()
        this.fragment.changeView(onErrorView)
    }

    init { this.fragment.changeView(onLoadView) }

}