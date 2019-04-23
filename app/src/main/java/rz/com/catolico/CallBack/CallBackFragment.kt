package rz.com.catolico.CallBack

import android.support.v4.app.Fragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rz.com.catolico.R
import rz.com.catolico.fragments.FragmentAbstract


open class CallBackFragment<T>(fragment: Fragment) : Callback<T> {

    private var fragmentAbstract: FragmentAbstract<*> = (fragment as FragmentAbstract<*>)

    override fun onResponse(call: Call<T>, response: Response<T>) {
        if (response.isSuccessful) {
            this.fragmentAbstract.changeView(R.layout.recycler_view_abstract)
        }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        t.printStackTrace()
        this.fragmentAbstract?.changeView(R.layout.erro_screen_top)
    }

    init {
        if(fragment is FragmentAbstract<*>){
            this.fragmentAbstract = fragmentAbstract

        }
        fragmentAbstract?.changeView(R.layout.load_screen_fragment)
    }

}