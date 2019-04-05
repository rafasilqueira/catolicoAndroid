package rz.com.catolico.bean

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rz.com.catolico.R
import rz.com.catolico.fragments.AbstractFragment


open class CallBackFragment<T : AbstractFragment<T>>(type: T) : Callback<T> {

    private var fragment: AbstractFragment<T>? = null

    override fun onResponse(call: Call<T>, response: Response<T>) {
        if (response.isSuccessful) {
            this.fragment?.changeView(R.layout.abstract_recycler_view)
        }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        t.printStackTrace()
        this.fragment?.changeView(R.layout.erro_screen_top)
    }

    init {
        this.fragment = type
        type.changeView(R.layout.load_screen_fragment)
    }

}