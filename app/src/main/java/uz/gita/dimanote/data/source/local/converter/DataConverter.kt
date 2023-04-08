package uz.gita.dimanote.data.source.local.converter

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

object DataConverter {
    @TypeConverter
    fun fromTimeStampToDate(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun fromDateToTimeStamp(date: Date?): Long? {
        return date?.time
    }

    fun getCurrentTime(): String {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        return current.format(formatter)
    }
}