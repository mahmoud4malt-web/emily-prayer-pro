package com.emily.prayerpro.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.emily.prayerpro.data.model.PrayerTime
import kotlinx.coroutines.flow.Flow

@Dao
interface PrayerDao {
    @Query("SELECT * FROM prayer_times")
    fun getPrayerTimes(): Flow<List<PrayerTime>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(prayers: List<PrayerTime>)

    @Query("DELETE FROM prayer_times")
    suspend fun deleteAll()
}