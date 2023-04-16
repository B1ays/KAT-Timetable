package ru.blays.timetable.Compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.lifecycle.ViewModelProvider
import ru.blays.timetable.Compose.ComposeElements.custom_toolbar.NavigationVM
import ru.blays.timetable.Compose.Screens.GroupListScreen.GroupListScreenVM
import ru.blays.timetable.Compose.Screens.GroupListScreen.GroupListVMFactory
import ru.blays.timetable.Compose.Screens.RootElements
import ru.blays.timetable.Compose.Screens.TimeTableScreen.TimetableScreenVM
import ru.blays.timetable.Compose.Screens.TimeTableScreen.TimetableVMFactory
import ru.blays.timetable.Compose.Theme.AviakatTimetableTheme
import ru.blays.timetable.data.models.ObjectBox.Boxes.MyObjectBox


lateinit var mainViewModel: MainViewModel
lateinit var timetableViewModel: TimetableScreenVM
lateinit var groupListViewModel : GroupListScreenVM
lateinit var navigationVM: NavigationVM

@ExperimentalAnimationApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {

            val objectBoxManager = MyObjectBox.builder()
                .androidContext(applicationContext.applicationContext)
                .build()

            mainViewModel = ViewModelProvider(
                this,
                MainViewModelFactory(this, objectBoxManager)
            )[MainViewModel::class.java]

            timetableViewModel = ViewModelProvider(
                this,
                TimetableVMFactory(this, objectBoxManager)
            )[TimetableScreenVM::class.java]

            groupListViewModel = ViewModelProvider(
                this,
                GroupListVMFactory(this, objectBoxManager)
            )[GroupListScreenVM::class.java]

            navigationVM = ViewModelProvider(this)[NavigationVM::class.java]

        }

        if (actionBar != null) {
            actionBar!!.hide()
        }

        setContent {
//            InitSettings()
            AviakatTimetableTheme(/*darkTheme = ThemeState.isDarkMode, dynamicColor = ThemeState.isDynamicColor*/ darkTheme = true, dynamicColor = true) {
                RootElements()
            }
        }
    }
}