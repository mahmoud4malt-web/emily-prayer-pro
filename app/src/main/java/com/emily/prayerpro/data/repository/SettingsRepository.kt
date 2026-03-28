package com.emily.prayerpro.data.repository

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.emily.prayerpro.util.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = Constants.SETTINGS_DATASTORE_NAME)

@Singleton
class SettingsRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val dataStore = context.dataStore

    val calculationMethod: Flow<Int> = dataStore.data.map { it[CALC_METHOD] ?: Constants.METHOD_UMM_AL_QURA }
    val is12HourFormat: Flow<Boolean> = dataStore.data.map { it[IS_12_HOUR] ?: true }
    val autoAdhanEnabled: Flow<Boolean> = dataStore.data.map { it[AUTO_ADHAN] ?: true }
    val silentReminderEnabled: Flow<Boolean> = dataStore.data.map { it[SILENT_REMINDER] ?: true }

    suspend fun updateCalculationMethod(method: Int) {
        dataStore.edit { it[CALC_METHOD] = method }
    }

    suspend fun update12HourFormat(enabled: Boolean) {
        dataStore.edit { it[IS_12_HOUR] = enabled }
    }

    suspend fun updateAutoAdhan(enabled: Boolean) {
        dataStore.edit { it[AUTO_ADHAN] = enabled }
    }

    suspend fun updateSilentReminder(enabled: Boolean) {
        dataStore.edit { it[SILENT_REMINDER] = enabled }
    }

    companion object {
        private val CALC_METHOD = intPreferencesKey("calc_method")
        private val IS_12_HOUR = booleanPreferencesKey("is_12_hour")
        private val AUTO_ADHAN = booleanPreferencesKey("auto_adhan")
        private val SILENT_REMINDER = booleanPreferencesKey("silent_reminder")
    }
}