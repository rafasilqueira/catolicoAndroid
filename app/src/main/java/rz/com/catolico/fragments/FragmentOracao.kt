package rz.com.catolico.fragments

import retrofit2.Call
import retrofit2.Response
import rz.com.catolico.R
import rz.com.catolico.adapter.AdapterOracaoCategory
import rz.com.catolico.adapter.ViewHolder.VHOracaoCategory
import rz.com.catolico.bean.Oracao
import rz.com.catolico.callBack.CallBackFragment
import rz.com.catolico.interfaces.IFavorite
import rz.com.catolico.retrofit.RetrofitConfig
import rz.com.catolico.utils.Constantes.Companion.ORACAO_FRAGMENT_CONTENT_TAG


class FragmentOracao : FragmentAbstract<Oracao>(R.layout.recycler_view_adapter_oracao), IFavorite<Oracao> {

    private var adapter: AdapterOracaoCategory? = null
    private var showByCategory = true
    private var selectedVH : VHOracaoCategory? = null


    fun notifyDataSetChanged() {
        recyclerView?.adapter?.notifyDataSetChanged()
        syncronizeFavorites(mList)
        selectedVH?.recyclerView?.adapter?.notifyDataSetChanged()
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
        val call: Call<MutableList<Oracao>> = RetrofitConfig().OracaoService().getOracoes()
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

    fun swapFragment(oracao: Oracao, selectedVH : VHOracaoCategory) {
        this.selectedVH = selectedVH
        val fragment = FragmentOracaoContent.instance(oracao)
        fragmentManager!!.beginTransaction()
                .hide(this)
                .add(R.id.frame_layout, fragment, ORACAO_FRAGMENT_CONTENT_TAG)
                .addToBackStack(null)
                .commit()
    }

    private fun setupPrayByCategory(mItems: MutableList<Oracao>): Map<String, MutableList<Oracao>> {
        return mItems.map { it.categoriaOracao }
                .distinct()
                .sortedBy { it?.nome }
                .map {
                    it!!.nome to mItems
                            .filter { oracao -> oracao.categoriaOracao == it }
                            .sortedBy { it.nome }
                            .toMutableList()
                }
                .toMap()
    }

    private fun setupPrayAlphabetical(mItems: MutableList<Oracao>): Map<String, MutableList<Oracao>> {
        return mItems.map { it.nome[0] }
                .distinct()
                .sorted()
                .map {
                    it.toString() to mItems
                            .filter { oracao -> oracao.nome[0] == it }
                            .sortedBy { it.nome }
                            .toMutableList()
                }
                .toMap()
    }

}