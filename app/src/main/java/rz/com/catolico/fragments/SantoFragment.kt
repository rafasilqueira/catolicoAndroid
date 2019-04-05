package rz.com.catolico.fragments

import br.com.tupinamba.model.bean.Santo
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rz.com.catolico.R
import rz.com.catolico.bean.CallBackFragment
import rz.com.catolico.retrofit.RetrofitConfig

class SantoFragment : AbstractFragment<Santo>(){

    companion object {
        fun instance(): SantoFragment {
            return SantoFragment()
        }
    }

    override fun loadData() {
        changeView(R.layout.load_screen_fragment)
        val call: Call<List<Santo>> = RetrofitConfig().getSantoService().getLatests()

        call.enqueue(object : CallBackFragment<List<Santo>>(null) {

            override fun onResponse(call: Call<List<Santo>>, response: Response<List<Santo>>) {
                super.onResponse(call, response)
            }

            override fun onFailure(call: Call<List<Santo>>, t: Throwable) {
                super.onFailure(call, t)
            }
        })

        call.enqueue(object : Callback<List<Santo>> {

            override fun onResponse(call: Call<List<Santo>>, response: Response<List<Santo>>) {
                this@SantoFragment.changeView(R.layout.abstract_recycler_view)
                this@SantoFragment.mList = response.body()
                println(GsonBuilder().setPrettyPrinting().create().toJson(response.body()))
                setupAdapter(mList!!)
            }

            override fun onFailure(call: Call<List<Santo>>, t: Throwable) {
                t.printStackTrace()
                this@SantoFragment.changeView(R.layout.erro_screen_top)
            }

        })
    }

    override fun setupAdapter(list: List<Santo>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun itemClickListenr(type: Santo) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /*

    override fun setupAdapter(parentActivity : Activity,list: List<Santo>) {
        setupRecyclerView()
        //var myAdapter = AdapterSanto(parentActivity , list)
        adapterSanto = AdapterSanto(parentActivity, list!!)
        recyclerView?.adapter = adapterSanto
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        view = inflater.inflate(R.layout.abstract_recycler_view, container, false) as ViewGroup
        mInflater = inflater
        mContainer = container
        //setupRecyclerView()
        loadLatests()
        return view
    }*/

}