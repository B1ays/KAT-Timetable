package ru.blays.timetable.UI.Compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.lifecycle.ViewModelProvider
import ru.blays.timetable.UI.Compose.ComposeElements.navigation.NavigationVM
import ru.blays.timetable.UI.Compose.ComposeElements.navigation.NavigationVMFactory
import ru.blays.timetable.UI.Compose.Screens.GroupListScreen.GroupListScreenVM
import ru.blays.timetable.UI.Compose.Screens.GroupListScreen.GroupListVMFactory
import ru.blays.timetable.UI.Compose.Screens.SettingsScreen.SettingsScreenVM
import ru.blays.timetable.UI.Compose.Screens.SettingsScreen.SettingsScreenVMFactory
import ru.blays.timetable.UI.Compose.Screens.TimeTableScreen.TimetableScreenVM
import ru.blays.timetable.UI.Compose.Screens.TimeTableScreen.TimetableVMFactory
import ru.blays.timetable.UI.Compose.Theme.AviakatTimetableTheme
import ru.blays.timetable.UI.Screens.RootElements
import ru.blays.timetable.data.models.ObjectBox.Boxes.MyObjectBox
import ru.blays.timetable.domain.repository.MediatingRepository.MediatingRepository


lateinit var mainViewModel: MainViewModel
lateinit var timetableViewModel: TimetableScreenVM
lateinit var groupListViewModel : GroupListScreenVM
lateinit var navigationViewModel: NavigationVM
lateinit var settingsViewModel: SettingsScreenVM

@ExperimentalAnimationApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {

            val objectBoxManager = MyObjectBox.builder()
                .androidContext(applicationContext.applicationContext)
                .build()

            val mediatingRepository = MediatingRepository()

            settingsViewModel = ViewModelProvider(
                this,
                SettingsScreenVMFactory(this, mediatingRepository)
            )[SettingsScreenVM::class.java]

            mainViewModel = ViewModelProvider(
                this,
                MainViewModelFactory(mediatingRepository, this)
            )[MainViewModel::class.java]

            timetableViewModel = ViewModelProvider(
                this,
                TimetableVMFactory(this, objectBoxManager, mediatingRepository)
            )[TimetableScreenVM::class.java]

            groupListViewModel = ViewModelProvider(
                this,
                GroupListVMFactory(this, objectBoxManager)
            )[GroupListScreenVM::class.java]

            navigationViewModel = ViewModelProvider(
                this,
                NavigationVMFactory(mediatingRepository)
            )[NavigationVM::class.java]

        }

        if (actionBar != null) {
            actionBar!!.hide()
        }

        setContent {
            AviakatTimetableTheme(darkTheme = mainViewModel.isDarkMode, dynamicColor = mainViewModel.monetColors) {
                mainViewModel.systemTheme = isSystemInDarkTheme()
                mainViewModel.init()
                RootElements()
            }
        }
    }
}