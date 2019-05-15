package rz.com.catolico.fragments

import android.app.Dialog
import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.DatePicker
import android.widget.Toast
import rz.com.catolico.bean.Santo
import retrofit2.Call
import retrofit2.Response
import rz.com.catolico.R
import rz.com.catolico.adapter.AdapterOracao
import rz.com.catolico.adapter.AdapterSanto
import rz.com.catolico.bean.Oracao
import rz.com.catolico.callBack.CallBackDialog
import rz.com.catolico.callBack.CallBackFragment
import rz.com.catolico.retrofit.RetrofitConfig
import rz.com.catolico.utils.ToastMisc
import java.util.*
import kotlin.collections.ArrayList

class FragmentSanto : FragmentAbstract<Santo>(R.layout.recycler_view_adapter_santo) {

    private var adapterSanto: AdapterSanto? = null
    private var dialogDatePicker: Dialog? = null

    companion object {
        fun instance(): FragmentSanto {
            return FragmentSanto()
        }
    }

    override fun loadData() {
        changeView(R.layout.load_screen_fragment)
        val call: Call<MutableList<Santo>> = RetrofitConfig().santoService().getLatests()

        call.enqueue(object : CallBackFragment<MutableList<Santo>>(this@FragmentSanto) {

            override fun onResponse(call: Call<MutableList<Santo>>, response: Response<MutableList<Santo>>) {
                super.onResponse(call, response)
                this@FragmentSanto.mList = response.body() ?: ArrayList()
                //println(GsonBuilder().setPrettyPrinting().create().toJson(response.body()))
                setupAdapter(mList)
            }

            override fun onFailure(call: Call<MutableList<Santo>>, t: Throwable) {
                super.onFailure(call, t)
                this@FragmentSanto.changeView(R.layout.erro_screen_top)
                disableAllIcons()
            }
        })
    }

    override fun setupAdapter(list: MutableList<Santo>) {
        setupRecyclerView()
        adapterSanto = AdapterSanto(parentActivity!!, this@FragmentSanto, list!!)
        recyclerView?.adapter = adapterSanto
    }

    fun showDialogDatePickerResult(list: MutableList<Santo>){
        var dialogResult = Dialog(activity!!)
        dialogResult.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogResult.setCancelable(true)
        dialogResult.setContentView(R.layout.dialog_santo_dia)
        val lp = WindowManager.LayoutParams()
        val window = dialogResult.getWindow()
        lp.copyFrom(window!!.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        window!!.setAttributes(lp)
        val recyclerView = dialogResult.findViewById(R.id.recyclerview) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = AdapterSanto(parentActivity!!, this, list)
        dialogResult.show()
        dialogResult.setCanceledOnTouchOutside(true)
    }

    fun showDialogDatePicker() {
        var calendar = Calendar.getInstance()

        dialogDatePicker = Dialog(activity!!)
        dialogDatePicker?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogDatePicker?.setCancelable(false)
        if (android.os.Build.VERSION.SDK_INT <= android.os.Build.VERSION_CODES.LOLLIPOP)
            dialogDatePicker?.setContentView(R.layout.dialog_search_by_date_lollipop)
        else
            dialogDatePicker?.setContentView(R.layout.dialog_search_by_date)

        val lp = WindowManager.LayoutParams()
        val window = dialogDatePicker?.getWindow()
        lp.copyFrom(window!!.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        window.attributes = lp
        var datePicker = dialogDatePicker?.findViewById<View>(R.id.date_picker) as DatePicker


        var year = calendar?.get(Calendar.YEAR)!!
        var month = calendar?.get(Calendar.MONTH)!!
        var day = calendar?.get(Calendar.DAY_OF_MONTH)!!


        datePicker.init(year,month,day) { view, year, monthOfYear, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        }


        dialogDatePicker!!.findViewById<View>(R.id.btn_search_liturgia).setOnClickListener {
            val dateToSearch = calendar.timeInMillis
            val call = RetrofitConfig().santoService().getByCelebrationDate(dateToSearch)
            call.enqueue(object : CallBackDialog<MutableList<Santo>>(parentActivity as Context) {

                override fun onResponse(call: Call<MutableList<Santo>>, response: Response<MutableList<Santo>>) {
                    super.onResponse(call, response)
                    //println(GsonBuilder().setPrettyPrinting().create().toJson(response.body()))
                    if(!response.body()!!.isEmpty()){
                        showDialogDatePickerResult(response.body()!!)
                        dialogDatePicker?.dismiss()
                    }else{
                        Toast.makeText(parentActivity!!,R.string.santo_search_result,Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<MutableList<Santo>>, t: Throwable) {
                    ToastMisc.generalError(parentActivity!!)
                    super.onFailure(call, t)
                }
            })

        }
        dialogDatePicker?.show()
        dialogDatePicker?.setCanceledOnTouchOutside(true)
    }

    override fun itemClickListenr(type: Santo) {

    }

    fun showDialogSayntPrays(list: MutableList<Oracao>) {
        var dialogResult = Dialog(activity!!)
        dialogResult.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogResult.setCancelable(true)
        dialogResult.setContentView(R.layout.dialog_oracoes)
        val lp = WindowManager.LayoutParams()
        val window = dialogResult.getWindow()
        lp.copyFrom(window!!.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        window!!.setAttributes(lp)
        val recyclerView = dialogResult.findViewById(R.id.recyclerview) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = AdapterOracao(parentActivity!!, this@FragmentSanto, list)
        dialogResult.show()
        dialogResult.setCanceledOnTouchOutside(true)
    }

}