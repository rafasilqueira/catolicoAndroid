package rz.com.catolico.fragments

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_liturgia.*
import retrofit2.Call
import retrofit2.Response
import rz.com.catolico.R
import rz.com.catolico.activiy.ActivityCatolicoMain
import rz.com.catolico.adapter.AdapterLiturgia
import rz.com.catolico.adapter.AdapterViewPagerLeitura
import rz.com.catolico.bean.Leitura
import rz.com.catolico.bean.Liturgia
import rz.com.catolico.callBack.CallBackFragment
import rz.com.catolico.interfaces.IAdapter.Companion.HORIZONTAL
import rz.com.catolico.retrofit.RetrofitConfig

class FragmentLiturgia : FragmentAbstractViewPager<Liturgia, ActivityCatolicoMain>() {

    private var adapterLiturgia: AdapterLiturgia? = null
    private var viewPagerAdapter: AdapterViewPagerLeitura? = null

    companion object {
        fun instance(): FragmentLiturgia {
            return FragmentLiturgia()
        }
    }

    override fun loadData() {
        val call: Call<MutableList<Liturgia>> = RetrofitConfig().liturgiaService().getLiturgias()
        call.enqueue(object : CallBackFragment<MutableList<Liturgia>>(this@FragmentLiturgia, R.layout.fragment_liturgia) {
            override fun onResponse(call: Call<MutableList<Liturgia>>, response: Response<MutableList<Liturgia>>) {
                super.onResponse(call, response)
                onSucessLoadData(response.body() ?: ArrayList())
                this@FragmentLiturgia.mList = response.body() ?: ArrayList()

            }

            override fun onFailure(call: Call<MutableList<Liturgia>>, t: Throwable) {
                super.onFailure(call, t)
                onErrorLoadData()
            }
        })
    }

    override fun saveInstance() {
        getParentActivity().fragmentLiturgiaSavedInstace = this
    }

    override fun onSucessLoadData(list: MutableList<Liturgia>) {
        getParentActivity().setupFragmentIcons(this)
        saveInstance()
        setupAdapter(list)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_liturgia, container, false) as ViewGroup
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (getParentActivity().fragmentLiturgiaSavedInstace != null) {
            setupAdapter(mList)
            getParentActivity().setupFragmentIcons(this)
        } else {
            loadData()
        }
    }

    override fun setupAdapter(mList: MutableList<Liturgia>) {
        val recyclerView = view?.findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView?.layoutManager = getLinearLayoutManager(getParentActivity(), HORIZONTAL)
        adapterLiturgia = AdapterLiturgia(getParentActivity(), mList, this)
        recyclerView?.adapter = adapterLiturgia
        setupViewPager()
        try {
            setLiturgiaViewPagerContent(mList.first { it.today }.leituras)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun setupViewPager() {
        tabLayout.tabMode = TabLayout.MODE_SCROLLABLE
        viewPagerAdapter = AdapterViewPagerLeitura(childFragmentManager)
    }

    private fun setLiturgiaViewPagerContent(leituras: MutableList<Leitura>) {
        viewPagerAdapter?.clear()
        viewPagerAdapter?.addAllFrags(leituras)
        viewPager.adapter = viewPagerAdapter
        tabLayout.setupWithViewPager(viewPager)
        viewPager.adapter!!.notifyDataSetChanged()
    }

    override fun onItemClick(type: Liturgia) {
        setLiturgiaViewPagerContent(type.leituras)
    }


}