package rz.com.catolico.fragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.tupinamba.model.bean.Santo
import kotlinx.android.synthetic.main.fragment_santo.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rz.com.catolico.R
import rz.com.catolico.activiy.CatolicoMainActivity
import rz.com.catolico.adapter.AdapterSanto
import rz.com.catolico.interfaces.IFragment
import rz.com.catolico.retrofit.RetrofitConfig

class SantoFragment : Fragment(), IFragment {

    private var view: ViewGroup? = null
    private var parentActivity: Activity? = null
    private var santosList: List<Santo>? = null
    private var adapterSanto: AdapterSanto? = null
    private var mInflater: LayoutInflater? = null
    private var mContainer: ViewGroup? = null
    private var recyclerView : RecyclerView? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        this.parentActivity = context as CatolicoMainActivity
    }

    companion object {
        fun instance(): SantoFragment {
            return SantoFragment()
        }
    }

    private fun loadLatests() {
        super.changeView(R.layout.load_screen_programacao, view!!, mInflater!!, mContainer)
        val call: Call<List<Santo>> = RetrofitConfig().getSantoService().getLatests()
        call.enqueue(object : Callback<List<Santo>> {

            override fun onResponse(call: Call<List<Santo>>, response: Response<List<Santo>>) {
                this@SantoFragment.changeView(R.layout.fragment_santo, view!!, mInflater!!, mContainer)
                this@SantoFragment.santosList = response.body()
                setupAdapter()
            }

            override fun onFailure(call: Call<List<Santo>>, t: Throwable) {
                t.printStackTrace()
                this@SantoFragment.changeView(R.layout.erro_screen_top, view!!, mInflater!!, mContainer)
            }

        })
    }

    override fun setupAdapter() {
        adapterSanto = AdapterSanto(parentActivity!!, santosList!!)
        recyclerView?.adapter = adapterSanto
    }

    private fun setupRecyclerView() {
        recyclerView?.layoutManager = LinearLayoutManager(parentActivity)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        view = inflater.inflate(R.layout.fragment_santo, container, false) as ViewGroup
        recyclerView = view?.findViewById(R.id.recyclerview)
        mInflater = inflater
        mContainer = container
        setupRecyclerView()
        loadLatests()
        return view
    }

}