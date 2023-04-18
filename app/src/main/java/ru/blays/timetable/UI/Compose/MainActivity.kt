package ru.blays.timetable.UI.Compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
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
import ru.blays.timetable.UI.DataClasses.AccentColorList
import ru.blays.timetable.UI.DataClasses.buildTheme
import ru.blays.timetable.UI.ScreenList
import ru.blays.timetable.UI.Screens.RootElements
import ru.blays.timetable.data.models.ObjectBox.Boxes.MyObjectBox

@ExperimentalAnimationApi
class MainActivity : ComponentActivity() {

    private val objectBoxManager by lazy {
        MyObjectBox.builder()
            .androidContext(applicationContext.applicationContext)
            .build()
    }

    private val mainViewModel by lazy {
        ViewModelProvider(
        this,
        MainViewModelFactory(this)
        )[MainViewModel::class.java]
    }

    private val timetableViewModel by lazy {
        ViewModelProvider(
        this,
        TimetableVMFactory(this, objectBoxManager)
        )[TimetableScreenVM::class.java]
    }

    private val groupListViewModel by lazy {
        ViewModelProvider(
        this,
        GroupListVMFactory(this, objectBoxManager)
        )[GroupListScreenVM::class.java]
    }

    private val navigationViewModel by lazy {
        ViewModelProvider(
        this,
        NavigationVMFactory()
        )[NavigationVM::class.java]
    }

    private val settingsViewModel by lazy {
        ViewModelProvider(
        this,
        SettingsScreenVMFactory(this)
        )[SettingsScreenVM::class.java]
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (actionBar != null) {
            actionBar!!.hide()
        }

        setContent {
            InitApp(
                mainViewModel,
                timetableViewModel,
                groupListViewModel,
                navigationViewModel,
                settingsViewModel
            )
            AviakatTimetableTheme(darkTheme = mainViewModel.isDarkMode, dynamicColor = mainViewModel.monetColors,
                mainViewModel.buildedTheme
            ) {
                mainViewModel.systemTheme = isSystemInDarkTheme()
                mainViewModel.init()
                RootElements(
                    mainViewModel,
                    timetableViewModel,
                    groupListViewModel,
                    navigationViewModel,
                    settingsViewModel
                )
            }
        }
    }
}

@Composable
fun InitApp(
    mainViewModel: MainViewModel,
    timetableViewModel: TimetableScreenVM,
    groupListViewModel: GroupListScreenVM,
    navigationViewModel: NavigationVM,
    settingsViewModel: SettingsScreenVM
) {
    with(mainViewModel) {
        systemTheme = isSystemInDarkTheme()
        isDarkMode = when(initialSettings.appTheme) {
            0 -> systemTheme
            1 -> true
            2 -> false
            else -> systemTheme
        }
        monetColors = initialSettings.monetTheme ?: true


        /*with(AccentColorList.list[mainViewModel.initialSettings.accentColor!!]) {
            if (!initialSettings.monetTheme!!) buildedTheme?.value = buildTheme(
                colorDark = accentDark,
                lightColor = accentLight
            )
        }*/
    }

    GlobalObserver(
        mainViewModel,
        timetableViewModel,
        groupListViewModel,
        navigationViewModel,
        settingsViewModel
    )
}

@Composable
fun GlobalObserver(
    mainViewModel: MainViewModel,
    timetableViewModel: TimetableScreenVM,
    groupListViewModel: GroupListScreenVM,
    navigationViewModel: NavigationVM,
    settingsViewModel: SettingsScreenVM
) {
    // observe navigateBackButton visible
    mainViewModel.navigateBackButtonVisible = navigationViewModel.currentScreen.Screen != ScreenList.MAIN_SCREEN
    mainViewModel.favoriteButtonVisible = navigationViewModel.currentScreen.Screen == ScreenList.TIMETABLE_SCREEN
    mainViewModel.subtitleVisible = mainViewModel.favoriteButtonVisible

    // observe appBar title

    with(mainViewModel) {
        isDarkMode = when (settingsViewModel.themeSelectionState) {
            0 -> systemTheme
            1 -> true
            2 -> false
            else -> systemTheme
        }
    }

    mainViewModel.monetColors = (settingsViewModel.monetTheme ?: mainViewModel.initialSettings.monetTheme)!!

    with(AccentColorList.list[settingsViewModel.accentColorIndex ?: mainViewModel.initialSettings.accentColor!!]) {
        if (!settingsViewModel.monetTheme!!) mainViewModel.buildedTheme = buildTheme(
            colorDark = accentDark,
            lightColor = accentLight
        )
    }

    with(mainViewModel) {
        when(navigationViewModel.currentScreen.Screen) {
            ScreenList.MAIN_SCREEN -> titleText = "Главная"
            ScreenList.ABOUT_SCREEN -> titleText = "О приложении"
            ScreenList.SETTINGS_SCREEN -> titleText = "Настройки"
            ScreenList.TIMETABLE_SCREEN -> {
                subtitleText = timetableViewModel.timetable.updateDate
                titleText = timetableViewModel.timetable.groupCode
            }
        }
    }
}
