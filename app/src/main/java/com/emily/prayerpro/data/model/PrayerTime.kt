package com.emily.prayerpro.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "prayer_times")
data class PrayerTime(
    @PrimaryKey val name: String,
    val time: String,
    val isCurrent: Boolean = false
)

data class PrayerUiState(
    val hijriDate: String = "",
    val gregorianDate: String = "",
    val currentTime: String = "00:00:00",
    val prayerTimes: List<PrayerTime> = emptyList(),
    val qiblaDegrees: Float = 0f,
    val nextPrayerName: String = "",
    val countdown: String = "--:--:--"
)