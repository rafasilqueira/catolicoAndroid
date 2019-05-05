package rz.com.catolico.fragments

import retrofit2.Call
import retrofit2.Response
import rz.com.catolico.CallBack.CallBackFragment
import rz.com.catolico.R
import rz.com.catolico.adapter.AdapterOracao
import rz.com.catolico.bean.Oracao
import rz.com.catolico.retrofit.RetrofitConfig

class FragmentOracao : FragmentAbstract<Oracao>(R.layout.recycler_view_adapter_oracao) {

    private var adapterOracao: AdapterOracao? = null

    companion object {
        fun instance(): FragmentOracao {
            return FragmentOracao()
        }
    }

    override fun setupAdapter(mItems: MutableList<Oracao>) {
        setupRecyclerView()
        adapterOracao = AdapterOracao(parentActivity!!, mItems)
        recyclerView?.adapter = adapterOracao
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
                if(response.isSuccessful){
                    this@FragmentOracao.mList = response.body()
                    //println(GsonBuilder().setPrettyPrinting().create().toJson(response.body()))
                    setupAdapter(mList!!)
                }
            }

            override fun onFailure(call: Call<MutableList<Oracao>>, t: Throwable) {
                super.onFailure(call, t)
                this@FragmentOracao.changeView(R.layout.erro_screen_top)
                disableAllIcons()
            }
        })
    }

}