package rz.com.catolico.adapter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import rz.com.catolico.R
import rz.com.catolico.adapter.ViewHolder.VHLiturgia
import rz.com.catolico.bean.Liturgia
import rz.com.catolico.fragments.FragmentAbstract
import rz.com.catolico.fragments.FragmentLiturgia
import java.util.*

class AdapterLiturgia(context: Context, mItem: MutableList<Liturgia>, var fragmentAbstract: Fragment) : AdapterAbstract<Liturgia>(context, mItem) {

    private val calendar: Calendar = Calendar.getInstance()

    enum class MonthOfYear(val order: Int, val description: String) {
        JAN(0, "JAN"),
        FEB(1, "FEV"),
        MAR(2, "MAR"),
        ABR(3, "ABR"),
        MAI(4, "MAI"),
        JUN(5, "JUN"),
        JUL(6, "JUL"),
        AGO(7, "AGO"),
        SET(8, "SET"),
        OUT(9, "OUT"),
        NOV(10, "NOV"),
        DEZ(11, "DEZ");

        companion object {
            fun getByMonthOfYear(monthOfyear: Int): MonthOfYear = values().first { it.order == monthOfyear }
        }

    }

    enum class BorderColorEnum(val id: Int, val rgbColor: String, val drawable: Int) {
        WHITE(1, "#FFFFFFFF", R.drawable.customborder_white),
        PURPLE(2, "#5F04B4", R.drawable.customborder_purple),
        RED(3, "#DF0101", R.drawable.customborder_red),
        GREEN(4, "#04B404", R.drawable.customborder_green),
        BLACK(5, "#FF000000", R.drawable.customborder_black),
        PINK(6, "#FF0040", R.drawable.customborder_pink),
        BLUE(7, "#0000FF", R.drawable.customborder_blue),
        GOLD(8, "#FFBF00", R.drawable.customborder_gold);

        companion object {
            fun getByRGBColor(rgbColor: String): BorderColorEnum = values().first { it.rgbColor == rgbColor }
        }


    }

    override fun setupViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.adapter_liturgia, parent, false)
        return VHLiturgia(context, view)
    }

    override fun onBindData(holder: RecyclerView.ViewHolder, genericType: Liturgia) {
        val view: VHLiturgia
        if (holder is VHLiturgia) {
            view = holder
            calendar.time = genericType.data
            view.txtMonth.text = MonthOfYear.getByMonthOfYear(calendar.get(Calendar.MONTH)).description
            view.txtDay.text = "%02d".format(calendar.get(Calendar.DAY_OF_MONTH))
            view.frameLayout.setBackgroundResource(BorderColorEnum.getByRGBColor(genericType.cor.rgb).drawable)

            view.setOnClickListener(View.OnClickListener {
                if (fragmentAbstract is FragmentLiturgia) {
                    (fragmentAbstract as FragmentLiturgia).itemClickListener(genericType)
                }
            })
        }
    }

}