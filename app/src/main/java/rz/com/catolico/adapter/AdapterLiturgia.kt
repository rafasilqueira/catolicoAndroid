package rz.com.catolico.adapter

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import rz.com.catolico.R
import rz.com.catolico.adapter.ViewHolder.VHLiturgia
import rz.com.catolico.bean.Liturgia
import rz.com.catolico.fragments.FragmentAbstract
import java.util.*

class AdapterLiturgia(context: Context, mItem: MutableList<Liturgia>, fragmentAbstract: FragmentAbstract<Liturgia>) : AdapterAbstract<Liturgia>(context, mItem) {


    val calendar = Calendar.getInstance()

    override fun setupViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.adapter_liturgia, parent, false)
        return VHLiturgia(context, view)
    }

    override fun onBindData(holder: RecyclerView.ViewHolder, genericType: Liturgia) {
        val view: VHLiturgia
        if (holder is VHLiturgia) {
            view = holder
            calendar.time = genericType.data
            view.txtDay.text = "%02d".format(calendar.get(Calendar.DAY_OF_MONTH))
            view.txtMonth.text = "%02d".format(calendar.get(Calendar.MONTH))
            view.frameLayout.setBackgroundColor(Color.parseColor(genericType.cor?.rgb))

        }
    }
}