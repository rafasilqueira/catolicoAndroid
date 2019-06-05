package rz.com.catolico.fragments

import retrofit2.Call
import retrofit2.Response
import rz.com.catolico.R
import rz.com.catolico.adapter.AdapterOracao
import rz.com.catolico.adapter.AdapterOracaoCategory
import rz.com.catolico.bean.Oracao
import rz.com.catolico.callBack.CallBackFragment
import rz.com.catolico.interfaces.IFavorite
import rz.com.catolico.retrofit.RetrofitConfig
import rz.com.catolico.utils.Constantes.Companion.ORACAO_FRAGMENT_CONTENT_TAG


class FragmentOracao : FragmentAbstract<Oracao>(R.layout.recycler_view_adapter_oracao), IFavorite<Oracao> {

    private var adapter: AdapterOracaoCategory? = null
    private var showByCategory = true
    var selectedAdapter: AdapterOracao? = null


    fun updateAdapter() {
        syncronizeFavorites(mList)
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
            var map = if (showByCategory) setupPrayByCategory(mItems) else setupPrayAlphabetical(mItems)
            adapter = AdapterOracaoCategory(parentActivity!!, this@FragmentOracao, map)
            recyclerView?.adapter = adapter
        }
    }

    override fun itemClickListenr(type: Oracao) {
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
                this@FragmentOracao.changeView(R.layout.erro_screen_top)
                disableAllIcons()
            }
        })
    }

    fun syncronizeFavorites(mItems: MutableList<Oracao>) {
        if (usuario != null && usuario?.oracoes?.isNotEmpty()!!) {
            super.syncronizeFavorites(mItems, usuario?.oracoes!!)
        }
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
        val fragment = FragmentOracaoContent.instance(oracao)
        swapFragment(fragment, ORACAO_FRAGMENT_CONTENT_TAG)
    }

    private fun setupPrayByCategory(mItems: MutableList<Oracao>): Map<String, MutableList<Oracao>> {
        return mItems.map { it.categoriaOracao }
                .distinct()
                .sortedBy { it?.name }
                .map {
                    it!!.name to mItems
                            .filter { oracao -> oracao.categoriaOracao == it }
                            .sortedBy { it.name }
                            .toMutableList()
                }
                .toMap()
    }

    private fun setupPrayAlphabetical(mItems: MutableList<Oracao>): Map<String, MutableList<Oracao>> {
        return mItems.map { it.name[0] }
                .distinct()
                .sorted()
                .map {
                    it.toString() to mItems
                            .filter { oracao -> oracao.name[0] == it }
                            .sortedBy { it.name }
                            .toMutableList()
                }
                .toMap()
    }

}