package example.com.mymandir

import java.text.SimpleDateFormat
import java.util.*

object Constants {

    // Api
    const val API_BASE_URL = "http://staging.mymandir.com/"

    const val CONNECTION_TIMEOUT: Long = 60
    const val READ_TIMEOUT: Long = 60

    const val DISPLAY_FULL_DATE_FORMAT = "dd-MM-yyyy hh:mm:ss a"

    fun getFormatDate(date : Date): String? {
        val formatter : java.text.DateFormat = SimpleDateFormat(DISPLAY_FULL_DATE_FORMAT)
        return formatter.format(date)
    }
}