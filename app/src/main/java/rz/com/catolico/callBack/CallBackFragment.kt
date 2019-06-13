package rz.com.catolico.callBack

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rz.com.catolico.R
import rz.com.catolico.fragments.FragmentAbstract


open class CallBackFragment<T>(fragment: FragmentAbstract<*>) : Callback<T> {

    private var fragmentAbstract = fragment

    override fun onResponse(call: Call<T>, response: Response<T>) {
        if (response.isSuccessful) {
            fragmentAbstract.changeView(fragmentAbstract.initialView)
        }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        t.printStackTrace()
        fragmentAbstract.changeView(R.layout.erro_screen_top)
    }

    init {
        fragmentAbstract.changeView(R.layout.load_screen_fragment)
    }

}