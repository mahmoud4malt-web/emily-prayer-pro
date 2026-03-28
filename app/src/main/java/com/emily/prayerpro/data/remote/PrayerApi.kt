package com.emily.prayerpro.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface PrayerApi {
    @GET("timings")
    suspend fun getTimings(
        @Query("latitude") lat: Double,
        @Query("longitude") lng: Double,
        @Query("method") method: Int
    ): PrayerResponse
}

data class PrayerResponse(
    val data: PrayerData
)

data class PrayerData(
    val timings: Map<String, String>,
    val date: DateInfo,
    val meta: MetaInfo
)

data class DateInfo(
    val readable: String,
    val hijri: HijriInfo
)

data class HijriInfo(
    val day: String,
    val month: MonthInfo,
    val year: String,
    val weekday: WeekdayInfo
)

data class MonthInfo(
    val ar: String
)

data class WeekdayInfo(
    val ar: String
)

data class MetaInfo(
    val latitude: Double,
    val longitude: Double
)