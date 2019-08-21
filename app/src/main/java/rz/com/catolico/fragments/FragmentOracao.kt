package rz.com.catolico.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import retrofit2.Call
import retrofit2.Response
import rz.com.catolico.R
import rz.com.catolico.activiy.ActivityCatolicoMain
import rz.com.catolico.activiy.ActivitySelectedOracao
import rz.com.catolico.adapter.AdapterOracao
import rz.com.catolico.adapter.AdapterOracaoCategory
import rz.com.catolico.bean.Oracao
import rz.com.catolico.callBack.CallBackFragment
import rz.com.catolico.enumeration.FeatureCode.ACTIVITY_SELECTED_ORACAO
import rz.com.catolico.exception.CatolicoException
import rz.com.catolico.interfaces.IAdapter.Companion.VERTICAL
import rz.com.catolico.interfaces.IFiltered
import rz.com.catolico.interfaces.ISortOracao
import rz.com.catolico.retrofit.RetrofitConfig


class FragmentOracao : FragmentAbstractAdapter<Oracao, ActivityCatolicoMain>(), ISortOracao, IFiltered {

    private var adapter: AdapterOracaoCategory? = null
    private var showByCategory = true
    var selectedAdapter: AdapterOracao? = null
    private lateinit var recyclerView: RecyclerView

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        recyclerView.adapter?.notifyDataSetChanged()
        selectedAdapter?.syncronize()
        selectedAdapter?.notifyDataSetChanged()
    }

    companion object {
        fun instance(): FragmentOracao = FragmentOracao()
    }

    override fun setupAdapter(mList: MutableList<Oracao>) {
        if (mList.isNotEmpty()) {
            recyclerView = view?.findViewById<RecyclerView>(R.id.recyclerview)
                    ?: throw CatolicoException("recycler view is null")
            recyclerView.layoutManager = getLinearLayoutManager(getParentActivity(), VERTICAL)
            val map = if (showByCategory) sortByCategory(mList) else sortAlphabeticalMap(mList)
            adapter = AdapterOracaoCategory(getParentActivity(), this, map)
            recyclerView.adapter = adapter
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_oracao, container, false) as ViewGroup
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (getParentActivity().fragmentOracaoSavedInstance != null) {
            setupAdapter(mList)
            getParentActivity().setupFragmentIcons(this)
        } else {
            loadData()
        }
    }

    override fun loadData() {
        val call: Call<MutableList<Oracao>> = RetrofitConfig().oracaoService().getOracoes()
        call.enqueue(object : CallBackFragment<MutableList<Oracao>>(this@FragmentOracao, R.layout.fragment_oracao) {
            override fun onResponse(call: Call<MutableList<Oracao>>, response: Response<MutableList<Oracao>>) {
                super.onResponse(call, response)
                if (response.isSuccessful) {
                    onSucessLoadData(response.body() ?: ArrayList())
                }
            }

            override fun onFailure(call: Call<MutableList<Oracao>>, t: Throwable) {
                super.onFailure(call, t)
                onErrorLoadData()
            }
        })
    }

    override fun saveInstance() {
        getParentActivity().fragmentOracaoSavedInstance = this
    }

    override fun onSucessLoadData(list: MutableList<Oracao>) {
        getParentActivity().setupFragmentIcons(this)
        saveInstance()
        setupAdapter(list)
    }

    override fun alphabeticalFilterListener() {
        showByCategory = false
        setupAdapter(mList)
    }

    override fun categoryFilterListener() {
        showByCategory = true
        setupAdapter(mList)
    }

    override fun onItemClick(type: Oracao) {
        startActivityForResult(Intent(activity, ActivitySelectedOracao::class.java).putExtra("oracao", type), ACTIVITY_SELECTED_ORACAO.code)
    }


}