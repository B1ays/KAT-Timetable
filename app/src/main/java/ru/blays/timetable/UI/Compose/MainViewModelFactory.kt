package ru.blays.timetable.UI.Compose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.blays.timetable.domain.repository.MediatingRepository

class MainViewModelFactory( private val mediatingRepository: MediatingRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(mediatingRepository) as T
    }
}