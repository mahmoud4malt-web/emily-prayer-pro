package com.emily.prayerpro.di

import android.content.Context
import androidx.room.Room
import com.emily.prayerpro.data.local.PrayerDao
import com.emily.prayerpro.data.local.PrayerDatabase
import com.emily.prayerpro.data.remote.PrayerApi
import com.emily.prayerpro.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePrayerDatabase(@ApplicationContext context: Context): PrayerDatabase {
        return Room.databaseBuilder(
            context,
            PrayerDatabase::class.java,
            Constants.PRAYER_DB_NAME
        ).build()
    }

    @Provides
    fun providePrayerDao(database: PrayerDatabase): PrayerDao {
        return database.prayerDao()
    }

    @Provides
    @Singleton
    fun providePrayerApi(): PrayerApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PrayerApi::class.java)
    }
}