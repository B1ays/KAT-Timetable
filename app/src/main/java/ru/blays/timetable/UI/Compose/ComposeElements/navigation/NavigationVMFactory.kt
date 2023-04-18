package ru.blays.timetable.UI.Compose.ComposeElements.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class NavigationVMFactory() : ViewModelProvider.Factory {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NavigationVM() as T
    }

}