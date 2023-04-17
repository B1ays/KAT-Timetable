package ru.blays.timetable.UI.Compose.ComposeElements.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.blays.timetable.domain.repository.MediatingRepository.MediatingRepository

class NavigationVMFactory(private val mediatingRepository: MediatingRepository) : ViewModelProvider.Factory {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NavigationVM(mediatingRepository) as T
    }

}