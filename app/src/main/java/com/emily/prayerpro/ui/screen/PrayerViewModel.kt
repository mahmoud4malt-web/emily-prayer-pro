package com.emily.prayerpro.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emily.prayerpro.data.model.PrayerTime
import com.emily.prayerpro.data.model.PrayerUiState
import com.emily.prayerpro.data.repository.PrayerRepository
import com.emily.prayerpro.util.LocationHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class PrayerViewModel @Inject constructor(
    private val repository: PrayerRepository,
    private val locationHelper: LocationHelper
) : ViewModel() {

    private val _uiState = MutableStateFlow(PrayerUiState())
    val uiState: StateFlow<PrayerUiState> = _uiState.asStateFlow()

    init {
        observePrayerTimes()
        refreshLocationAndTimes()
        startClock()
    }

    private fun observePrayerTimes() {
        viewModelScope.launch {
            repository.getPrayerTimes().collect { prayers ->
                _uiState.update { it.copy(prayerTimes = prayers) }
                updateCurrentAndNextPrayer()
            }
        }
    }

    private fun refreshLocationAndTimes() {
        viewModelScope.launch {
            val location = locationHelper.getCurrentLocation()
            if (location != null) {
                val qibla = repository.calculateQibla(location.latitude, location.longitude)
                _uiState.update { it.copy(qiblaDegrees = qibla) }
                // In a real app, trigger a refresh from the API here
            }
        }
    }

    private fun startClock() {
        viewModelScope.launch {
            while (true) {
                val now = LocalTime.now()
                _uiState.update { 
                    it.copy(currentTime = now.format(DateTimeFormatter.ofPattern("HH:mm:ss")))
                }
                updateCountdown()
                delay(1000)
            }
        }
    }

    private fun updateCurrentAndNextPrayer() {
        val now = LocalTime.now()
        val prayers = _uiState.value.prayerTimes
        if (prayers.isEmpty()) return

        var next: PrayerTime? = null
        var current: PrayerTime? = null

        for (i in prayers.indices) {
            val pTime = try { LocalTime.parse(prayers[i].time) } catch(e: Exception) { null }
            if (pTime != null && pTime.isAfter(now)) {
                next = prayers[i]
                current = if (i > 0) prayers[i-1] else prayers.last()
                break
            }
        }

        if (next == null) {
            next = prayers.first()
            current = prayers.last()
        }

        _uiState.update { state ->
            state.copy(
                nextPrayerName = next.name,
                prayerTimes = state.prayerTimes.map { 
                    it.copy(isCurrent = it.name == current?.name)
                }
            )
        }
    }

    private fun updateCountdown() {
        val now = LocalTime.now()
        val prayers = _uiState.value.prayerTimes
        if (prayers.isEmpty()) return

        var nextTime: LocalTime? = null
        for (p in prayers) {
            val pTime = try { LocalTime.parse(p.time) } catch(e: Exception) { null }
            if (pTime != null && pTime.isAfter(now)) {
                nextTime = pTime
                break
            }
        }

        if (nextTime == null) {
            nextTime = try { LocalTime.parse(prayers.first().time) } catch(e: Exception) { LocalTime.MIDNIGHT }
        }

        val duration = if (nextTime.isAfter(now)) {
            Duration.between(now, nextTime)
        } else {
            Duration.between(now, LocalTime.MAX).plus(Duration.between(LocalTime.MIN, nextTime))
        }

        val hours = duration.toHours()
        val minutes = duration.toMinutes() % 60
        val seconds = duration.seconds % 60
        
        _uiState.update { 
            it.copy(countdown = String.format("%02d:%02d:%02d", hours, minutes, seconds))
        }
    }
}