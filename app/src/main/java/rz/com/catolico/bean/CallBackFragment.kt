package rz.com.catolico.bean

import android.support.v4.app.Fragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rz.com.catolico.R
import rz.com.catolico.fragments.AbstractFragment


open class CallBackFragment<T>(fragment: Fragment) : Callback<T> {

    private var abstractFragment: AbstractFragment<*> = (fragment as AbstractFragment<*>)

    override fun onResponse(call: Call<T>, response: Response<T>) {
        if (response.isSuccessful) {
            this.abstractFragment.changeView(R.layout.abstract_recycler_view)
        }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        t.printStackTrace()
        this.abstractFragment?.changeView(R.layout.erro_screen_top)
    }

    init {
        if(fragment is AbstractFragment<*>){
            this.abstractFragment = abstractFragment

        }
        abstractFragment?.changeView(R.layout.load_screen_fragment)
    }

}