package com.emily.prayerpro.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.emily.prayerpro.data.model.PrayerTime

@Database(entities = [PrayerTime::class], version = 1)
abstract class PrayerDatabase : RoomDatabase() {
    abstract fun prayerDao(): PrayerDao
}