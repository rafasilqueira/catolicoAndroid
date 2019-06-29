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
import rz.com.catolico.R
import rz.com.catolico.activiy.ActivityCatolicoMain
import rz.com.catolico.adapter.AdapterAbstractViewPager
import rz.com.catolico.bean.Santo
import rz.com.catolico.utils.SantoUtils.Companion.formatterComemoracao

class FragmentSelectedSanto : FragmentAbstract<ActivityCatolicoMain>() {

    private var tabLayout: TabLayout? = null
    private var viewPager: ViewPager? = null
    private var imgSanto: AppCompatImageView? = null
    private var santo: Santo? = null
    private var celebrationDay: String? = null

    private fun getArguments(key: String) = arguments?.getSerializable(key) as Santo?

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
        santo = getArguments("santo")
        celebrationDay = formatterComemoracao.format(santo?.comemoracao)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_selected_santo, container, false) as ViewGroup
        getParentActivity().isFavorite(santo!!.favorite)
        imgSanto = view.findViewById<AppCompatImageView>(R.id.imgSanto)
        tabLayout = view.findViewById<TabLayout>(R.id.tabLayout)
        viewPager = view.findViewById<ViewPager>(R.id.viewPager)
        if (santo?.imgurl != null && !santo?.imgurl.equals("")) Picasso.with(context).load(santo?.imgurl).into(imgSanto)
        santo?.let { setupViewPager(it) }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        disableAllIcons()
        getParentActivity().showIconsSelectedContent()
    }

    private fun setupViewPager(santo: Santo) {
        val viewPagerAdapter = AdapterAbstractViewPager(childFragmentManager)
        tabLayout?.tabMode = TabLayout.MODE_FIXED
        viewPagerAdapter.addFrag(FragmentSantoHistory.instance(santo), getString(R.string.historia))
        viewPagerAdapter.addFrag(FragmentSantoRelated.instance(santo), getString(R.string.related))
        tabLayout?.setupWithViewPager(viewPager)
        viewPager?.adapter = viewPagerAdapter
        getParentActivity().setActionBarTitle(celebrationDay ?: getString(R.string.app_name))
    }


    /*private fun favoriteButtonListener() {
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
                        getParentActivity().isFavorite(santo!!.favorite)
                    }
                }

                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    t.printStackTrace()
                }

            })
        }

    }*/

}
