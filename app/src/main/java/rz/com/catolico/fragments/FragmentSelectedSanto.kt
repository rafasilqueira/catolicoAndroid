package rz.com.catolico.fragments

import android.content.Context
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.widget.AppCompatImageView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rz.com.catolico.R
import rz.com.catolico.activiy.ActivityCatolicoMain
import rz.com.catolico.adapter.AdapterAbstractViewPager
import rz.com.catolico.bean.Santo
import rz.com.catolico.bean.Usuario
import rz.com.catolico.retrofit.RetrofitConfig
import rz.com.catolico.utils.SantoUtils.Companion.formatterComemoracao

class FragmentSelectedSanto : FragmentAbstract<ActivityCatolicoMain>(R.layout.fragment_selected_santo) {

    private var santo: Santo? = null
    private var tabLayout: TabLayout? = null
    private var viewPager : ViewPager? = null
    private var imgSanto : AppCompatImageView? = null

    private fun getArguments(key :String ) = arguments?.getSerializable(key) as Santo?


    companion object {
        fun instance(santo: Santo): FragmentSelectedSanto {
            val fragmentselectedSanto = FragmentSelectedSanto()
            val bundle = Bundle()
            bundle.putSerializable("santo", santo)
            fragmentselectedSanto.arguments = bundle
            return fragmentselectedSanto
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        usuario = parentActivity!!.getIntentUser()
        parentActivity!!.disableAllIcons()
        parentActivity!!.showIconsSelectedContent()
        val santo = getArguments("santo")
        val celebrationDay = santo?.let { formatterComemoracao.format(it.comemoracao) }
        parentActivity?.title = celebrationDay?.let { celebrationDay }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_selected_santo, container, false) as ViewGroup
        santo = getArguments("santo")
        parentActivity?.isFavorite(santo!!.favorite)
        imgSanto = view.findViewById<AppCompatImageView>(R.id.imgSanto)
        tabLayout = view.findViewById<TabLayout>(R.id.tabLayout)
        viewPager = view.findViewById<ViewPager>(R.id.viewPager)
        if (santo?.imgurl != null && !santo?.imgurl.equals("")) Picasso.with(context).load(santo?.imgurl).into(imgSanto)
        santo?.let { setupViewPager(it) }
        return view
    }

    fun setupViewPager(santo: Santo) {
        val viewPagerAdapter = AdapterAbstractViewPager(childFragmentManager)
        tabLayout?.tabMode = TabLayout.MODE_FIXED
        viewPagerAdapter.addFrag(FragmentSantoHistory.instance(santo), getString(R.string.historia))
        viewPagerAdapter.addFrag(FragmentSantoRelated.instance(santo), getString(R.string.related))
        tabLayout?.setupWithViewPager(viewPager)
        viewPager?.adapter = viewPagerAdapter
    }


    fun favoriteButtonListener() {
        if (usuario != null) {
            val userClone = usuario?.clone() as Usuario

            if (santo?.favorite!!) {
                userClone.removeSanto(santo!!)
            } else {
                userClone.addSanto(santo!!)
            }

            val call: Call<Boolean> = RetrofitConfig().usuarioService().saveUser(userClone)
            call.enqueue(object : Callback<Boolean> {

                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    if (response.isSuccessful) {
                        santo?.favorite = !santo?.favorite!!
                        if (santo?.favorite!!) {
                            usuario!!.addSanto(santo!!.clone() as Santo)
                        } else {
                            usuario!!.removeSanto(santo!!)
                        }
                        parentActivity?.isFavorite(santo!!.favorite)
                    }
                }

                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    t.printStackTrace()
                }

            })
        }

    }

}
