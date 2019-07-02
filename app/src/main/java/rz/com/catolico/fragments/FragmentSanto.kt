package rz.com.catolico.fragments

import android.app.Dialog
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.DatePicker
import android.widget.Toast
import retrofit2.Call
import retrofit2.Response
import rz.com.catolico.R
import rz.com.catolico.activiy.ActivityCatolicoMain
import rz.com.catolico.adapter.AdapterSanto
import rz.com.catolico.bean.Santo
import rz.com.catolico.callBack.CallBackDialog
import rz.com.catolico.callBack.CallBackFragment
import rz.com.catolico.retrofit.RetrofitConfig
import rz.com.catolico.utils.ToastMisc
import java.util.*
import kotlin.collections.ArrayList

class FragmentSanto : FragmentAbstractAdapter<Santo, ActivityCatolicoMain>() {

    private var adapterSanto: AdapterSanto? = null
    private var dialogDatePicker: Dialog? = null

    companion object {
        fun instance(): FragmentSanto {
            return FragmentSanto()
        }
    }

    fun updateAdapter() {
        adapterSanto?.notifyDataSetChanged()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_santo, container, false) as ViewGroup
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (getParentActivity().fragmentSantoSavedInstance != null && mList.isNotEmpty()) {
            setupAdapter(mList)
            getParentActivity().setupFragmentIcons(this)
        } else {
            loadData()
        }
    }

    override fun loadData() {
        val call: Call<MutableList<Santo>> = RetrofitConfig().santoService().getLatests()
        call.enqueue(object : CallBackFragment<MutableList<Santo>>(this@FragmentSanto, R.layout.fragment_santo) {
            override fun onResponse(call: Call<MutableList<Santo>>, response: Response<MutableList<Santo>>) {
                super.onResponse(call, response)
                this@FragmentSanto.mList = response.body() ?: ArrayList()
                onSucessLoadData()
                setupAdapter(mList)
            }

            override fun onFailure(call: Call<MutableList<Santo>>, t: Throwable) {
                super.onFailure(call, t)
                onErrorLoadData()
            }
        })
    }

    override fun saveInstance() {
        getParentActivity().fragmentSantoSavedInstance = this
    }

    override fun onSucessLoadData() {
        getParentActivity().setupFragmentIcons(this)
        saveInstance()
    }

    override fun setupAdapter(mList: MutableList<Santo>) {
        val recyclerView = view?.findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView?.layoutManager = getLinearLayoutManager(VERTICAL)
        adapterSanto = AdapterSanto(getParentActivity(), this@FragmentSanto, mList)
        recyclerView?.adapter = adapterSanto
    }

    fun showDialogDatePickerResult(list: MutableList<Santo>) {
        val dialogResult = Dialog(activity!!)
        dialogResult.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogResult.setCancelable(true)
        dialogResult.setContentView(R.layout.dialog_santo_dia)
        val lp = WindowManager.LayoutParams()
        val window = dialogResult.window
        lp.copyFrom(window!!.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        window.attributes = lp
        val recyclerView = dialogResult.findViewById(R.id.recyclerview) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = AdapterSanto(getParentActivity(), this, list)
        dialogResult.show()
        dialogResult.setCanceledOnTouchOutside(true)
    }

    fun showDialogDatePicker() {
        val calendar = Calendar.getInstance()

        dialogDatePicker = Dialog(activity!!)
        dialogDatePicker?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogDatePicker?.setCancelable(false)
        if (android.os.Build.VERSION.SDK_INT <= android.os.Build.VERSION_CODES.LOLLIPOP)
            dialogDatePicker?.setContentView(R.layout.dialog_search_by_date_lollipop)
        else
            dialogDatePicker?.setContentView(R.layout.dialog_search_by_date)

        val lp = WindowManager.LayoutParams()
        val window = dialogDatePicker?.window
        lp.copyFrom(window!!.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        window.attributes = lp
        val datePicker = dialogDatePicker?.findViewById<View>(R.id.date_picker) as DatePicker


        val year = calendar?.get(Calendar.YEAR)!!
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)


        datePicker.init(year, month, day) { _, year, monthOfYear, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        }


        dialogDatePicker!!.findViewById<View>(R.id.btn_search_liturgia).setOnClickListener {
            val dateToSearch = calendar.timeInMillis
            val call = RetrofitConfig().santoService().getByCelebrationDate(dateToSearch)
            call.enqueue(object : CallBackDialog<MutableList<Santo>>(getParentActivity().applicationContext) {

                override fun onResponse(call: Call<MutableList<Santo>>, response: Response<MutableList<Santo>>) {
                    super.onResponse(call, response)
                    //println(GsonBuilder().setPrettyPrinting().create().toJson(response.body()))
                    if (response.body()!!.isNotEmpty()) {
                        showDialogDatePickerResult(response.body()!!)
                        dialogDatePicker?.dismiss()
                    } else {
                        Toast.makeText(getParentActivity(), R.string.santo_search_result, Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<MutableList<Santo>>, t: Throwable) {
                    ToastMisc.generalError(getParentActivity())
                    super.onFailure(call, t)
                }
            })

        }
        dialogDatePicker?.show()
        dialogDatePicker?.setCanceledOnTouchOutside(true)
    }

}