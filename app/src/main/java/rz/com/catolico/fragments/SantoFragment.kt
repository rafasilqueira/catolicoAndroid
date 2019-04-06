package rz.com.catolico.fragments

import br.com.tupinamba.model.bean.Santo
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rz.com.catolico.R
import rz.com.catolico.adapter.AdapterSanto
import rz.com.catolico.bean.CallBackFragment
import rz.com.catolico.retrofit.RetrofitConfig

class SantoFragment : AbstractFragment<Santo>(){

    private var adapterSanto : AdapterSanto? = null

    companion object {
        fun instance(): SantoFragment {
            return SantoFragment()
        }
    }

    override fun loadData() {
        changeView(R.layout.load_screen_fragment)
        val call: Call<List<Santo>> = RetrofitConfig().getSantoService().getLatests()

        call.enqueue(object : CallBackFragment<List<Santo>>(this@SantoFragment) {

            override fun onResponse(call: Call<List<Santo>>, response: Response<List<Santo>>) {
                super.onResponse(call, response)
                this@SantoFragment.mList = response.body()
                println(GsonBuilder().setPrettyPrinting().create().toJson(response.body()))
                setupAdapter(mList!!)
            }

            override fun onFailure(call: Call<List<Santo>>, t: Throwable) {
                super.onFailure(call, t)
                this@SantoFragment.changeView(R.layout.erro_screen_top)
            }
        })
    }

    override fun setupAdapter(list: List<Santo>) {
        setupRecyclerView()
        adapterSanto = AdapterSanto(parentActivity!!, this@SantoFragment,list!!)
        recyclerView?.adapter = adapterSanto
    }

    override fun itemClickListenr(type: Santo) {
        println(type.descricao)
    }

}