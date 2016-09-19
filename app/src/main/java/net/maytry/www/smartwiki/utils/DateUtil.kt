package net.maytry.www.smartwiki.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by slont on 9/18/16.
 */

class DateUtil {
    companion object {
        val DATETIME_FORMAT_STRING = "yyyy/MM/dd HH:mm:ss"
        val DATETIME_FORMAT = SimpleDateFormat(DATETIME_FORMAT_STRING)

        fun stringToDate(string: String) = DATETIME_FORMAT.parse(string)
        fun dateToString(date: Date) = DATETIME_FORMAT.format(date)
    }
}