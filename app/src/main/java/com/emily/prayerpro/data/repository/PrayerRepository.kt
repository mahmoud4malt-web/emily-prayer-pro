package com.emily.prayerpro.data.repository

import com.emily.prayerpro.data.local.PrayerDao
import com.emily.prayerpro.data.model.PrayerTime
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PrayerRepository @Inject constructor(
    private val prayerDao: PrayerDao
) {
    fun getPrayerTimes(): Flow<List<PrayerTime>> = prayerDao.getPrayerTimes()

    suspend fun updatePrayerTimes(prayers: List<PrayerTime>) {
        prayerDao.deleteAll()
        prayerDao.insertAll(prayers)
    }

    // Qibla calculation logic (Mecca: 21.4225, 39.8262)
    fun calculateQibla(lat: Double, lng: Double): Float {
        val phiK = Math.toRadians(21.4225)
        val lambdaK = Math.toRadians(39.8262)
        val phi = Math.toRadians(lat)
        val lambda = Math.toRadians(lng)
        
        val qibla = Math.atan2(
            Math.sin(lambdaK - lambda),
            Math.cos(phi) * Math.tan(phiK) - Math.sin(phi) * Math.cos(lambdaK - lambda)
        )
        return ((Math.toDegrees(qibla) + 360) % 360).toFloat()
    }
}