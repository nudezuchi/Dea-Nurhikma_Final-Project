package id.ac.unhas.final_project

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoField

class Converter {
    companion object{
        @RequiresApi(Build.VERSION_CODES.O)
        fun dateToInt(time: ZonedDateTime): Int{
            return time.toInstant().epochSecond.toInt()
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun stringDateToInt(date: String): Int{
            val formatter = DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy")
            return LocalDate.parse(date, formatter).toEpochDay().toInt()
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun stringTimeToInt(time: String): Int{
            return LocalTime.parse(time).getLong(ChronoField.MILLI_OF_DAY).toInt()
        }
    }
}