package rz.com.catolico.fragments

import retrofit2.Call
import retrofit2.Response
import rz.com.catolico.CallBack.CallBackFragment
import rz.com.catolico.R
import rz.com.catolico.adapter.AdapterOracaoCategory
import rz.com.catolico.bean.Oracao
import rz.com.catolico.retrofit.RetrofitConfig


class FragmentOracao : FragmentAbstract<Oracao>(R.layout.recycler_view_adapter_oracao) {

    private var adapter: AdapterOracaoCategory? = null
    private var showByCategory = true

    companion object {
        fun instance(): FragmentOracao {
            return FragmentOracao()
        }
    }

    override fun setupAdapter(mItems: MutableList<Oracao>) {
        if (mItems.isNotEmpty()) {
            setupRecyclerView()
            var map = if (showByCategory) setupPrayByCategory(mItems) else setupPrayAlphabetical(mItems)
            adapter = AdapterOracaoCategory(parentActivity!!, map)
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
                        setupAdapter(mList)
                        //println(GsonBuilder().setPrettyPrinting().create().toJson(response.body()))
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

    fun showByCategory() {
        showByCategory = true
        setupAdapter(mList)
    }

    fun showAlphabetical() {
        showByCategory = false
        setupAdapter(mList)
    }

    private fun setupPrayByCategory(mItems: MutableList<Oracao>): Map<String, MutableList<Oracao>> {
        return mItems.map { it.categoriaOracao }
                .distinct()
                .sortedBy { it?.nome }
                .map { it!!.nome to mItems.filter { oracao -> oracao.categoriaOracao == it }
                        .sortedBy { it.nome }
                        .toMutableList() }
                .toMap()
    }

    private fun setupPrayAlphabetical(mItems: MutableList<Oracao>): Map<String, MutableList<Oracao>> {
        return mItems.map { it.nome[0] }
                .distinct()
                .sorted()
                .map { it.toString() to mItems.filter { oracao -> oracao.nome[0] == it }
                        .sortedBy { it.nome }
                        .toMutableList() }
                .toMap()
    }

}