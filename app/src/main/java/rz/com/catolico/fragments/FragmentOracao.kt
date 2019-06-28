package rz.com.catolico.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import retrofit2.Call
import retrofit2.Response
import rz.com.catolico.R
import rz.com.catolico.activiy.ActivityCatolicoMain
import rz.com.catolico.adapter.AdapterOracao
import rz.com.catolico.adapter.AdapterOracaoCategory
import rz.com.catolico.bean.Oracao
import rz.com.catolico.callBack.CallBackFragment
import rz.com.catolico.interfaces.IFavorite
import rz.com.catolico.interfaces.ISortOracao
import rz.com.catolico.retrofit.RetrofitConfig


class FragmentOracao : FragmentAbstractAdapter<Oracao, ActivityCatolicoMain>(R.layout.fragment_oracao), IFavorite<Oracao>, ISortOracao {

    override fun actionAfterAttachFragment() {
        getParentActivity().setupFragmentIcons(this)
    }

    private var adapter: AdapterOracaoCategory? = null
    private var showByCategory = true
    var selectedAdapter: AdapterOracao? = null


    fun updateAdapter() {
        getUser()?.let { super.syncronizeFavorites(mList, it.oracoes) }
        recyclerView?.adapter?.notifyDataSetChanged()
        selectedAdapter?.notifyDataSetChanged()
    }

    companion object {
        fun instance(): FragmentOracao {
            return FragmentOracao()
        }
    }

    override fun setupAdapter(list: MutableList<Oracao>) {
        if (list.isNotEmpty()) {
            setupRecyclerView()
            val map = if (showByCategory) sortByCategory(list) else sortAlphabeticalMap(list)
            adapter = AdapterOracaoCategory(getParentActivity(), this@FragmentOracao, map)
            recyclerView?.adapter = adapter
        }
    }

    override fun itemClickListener(type: Oracao) {
        println("Aqui porra!!!")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_oracao, container, false) as ViewGroup
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loadData()
    }

    override fun loadData() {
//        changeView(R.layout.fragment_load_screen)
        val call: Call<MutableList<Oracao>> = RetrofitConfig().oracaoService().getOracoes()
        call.enqueue(object : CallBackFragment<MutableList<Oracao>>(this@FragmentOracao, R.layout.fragment_oracao) {

            override fun onResponse(call: Call<MutableList<Oracao>>, response: Response<MutableList<Oracao>>) {
                super.onResponse(call, response)
                if (response.isSuccessful) {
                    this@FragmentOracao.mList = response.body() ?: ArrayList()
                    if (mList.isNotEmpty()) {
                        syncronizeFavorites(mList)
                        setupAdapter(mList)
                    }
                }
            }

            override fun onFailure(call: Call<MutableList<Oracao>>, t: Throwable) {
                super.onFailure(call, t)
                this@FragmentOracao.changeView(R.layout.fragment_erro_screen_top)
                disableAllIcons()
            }
        })
    }

    fun syncronizeFavorites(mItems: MutableList<Oracao>) {
        getUser()?.let { super.syncronizeFavorites(mItems, it.oracoes) }
    }


    fun showByCategory() {
        showByCategory = true
        setupAdapter(mList)
    }

    fun showAlphabetical() {
        showByCategory = false
        setupAdapter(mList)
    }

}