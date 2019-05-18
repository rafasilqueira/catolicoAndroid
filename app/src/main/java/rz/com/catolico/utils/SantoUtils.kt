package rz.com.catolico.utils

import android.content.Context
import rz.com.catolico.R
import java.text.SimpleDateFormat

class SantoUtils {

    companion object {
        val formatterComemoracao = SimpleDateFormat("dd/MM")

        fun getDaysToDate(context: Context, days: Int): String {
            val TODAY: String = ActivityUtils.getResourceString(context, R.string.hoje)
            val DAYS_TO_DATE: String = ActivityUtils.getResourceString(context, R.string.days_qtde)
            val IS_TODAY = 0
            var howManyDays: String? = null
            if (days == IS_TODAY) howManyDays = TODAY
            if (days > 0) howManyDays = ActivityUtils.getResourceString(context, R.string.at) + " %02d ".format(days) + DAYS_TO_DATE
            if (days < 0) howManyDays = ActivityUtils.getResourceString(context, R.string.ha) + " %02d ".format(days) + DAYS_TO_DATE
            return howManyDays!!
        }

    }
}