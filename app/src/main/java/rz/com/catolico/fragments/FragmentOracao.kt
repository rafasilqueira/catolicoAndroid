package rz.com.catolico.fragments

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
import rz.com.catolico.utils.Constantes.Companion.SELECTED_ORACAO_FRAGMENT_TAG


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

    override fun setupAdapter(mItems: MutableList<Oracao>) {
        if (mItems.isNotEmpty()) {
            setupRecyclerView()
            val map = if (showByCategory) sortByCategory(mItems) else sortAlphabeticalMap(mItems)
            adapter = AdapterOracaoCategory(getParentActivity(), this@FragmentOracao, map)
            recyclerView?.adapter = adapter
        }
    }

    override fun itemClickListener(type: Oracao) {
        println("Aqui porra!!!")
    }

    override fun loadData() {
        changeView(R.layout.load_screen_fragment)
        val call: Call<MutableList<Oracao>> = RetrofitConfig().oracaoService().getOracoes()
        call.enqueue(object : CallBackFragment<MutableList<Oracao>>(this@FragmentOracao) {

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

    fun showSelectedORacao(oracao: Oracao, selectedAdapter: AdapterOracao) {
        this.selectedAdapter = selectedAdapter
        val fragment = FragmentSelectedOracao.instance(oracao)
        swapFragment(fragment, SELECTED_ORACAO_FRAGMENT_TAG)
    }

}