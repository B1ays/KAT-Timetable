@file:OptIn(ExperimentalMaterialApi::class)

package ru.blays.timetable.UI.Screens

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.blays.timetable.UI.Compose.ComposeElements.CollapsingAppBar
import ru.blays.timetable.UI.Compose.ComposeElements.FloatingMenu
import ru.blays.timetable.UI.Compose.ComposeElements.UpdateInfo
import ru.blays.timetable.UI.Compose.ComposeElements.navigation.NavigationVM
import ru.blays.timetable.UI.Compose.MainViewModel
import ru.blays.timetable.UI.Compose.Screens.GroupListScreen.GroupListScreenVM
import ru.blays.timetable.UI.Compose.Screens.SettingsScreen.SettingsScreenVM
import ru.blays.timetable.UI.Compose.Screens.TimeTableScreen.TimetableScreenVM
import ru.blays.timetable.UI.ComposeElements.Navigation
import ru.blays.timetable.UI.ScreenList
import ru.hh.toolbar.custom_toolbar.rememberToolbarScrollBehavior

@ExperimentalAnimationApi
@Composable
fun RootElements(
    mainViewModel: MainViewModel,
    timetableViewModel: TimetableScreenVM,
    groupListViewModel: GroupListScreenVM,
    navigationViewModel: NavigationVM,
    settingsViewModel: SettingsScreenVM,
    updateDialog: UpdateInfo
) {


    val scrollBehavior = rememberToolbarScrollBehavior()

    val isRefreshing = when (navigationViewModel.currentScreen.Screen) {
        ScreenList.TIMETABLE_SCREEN -> { timetableViewModel.isRefreshing }
        ScreenList.MAIN_SCREEN -> { groupListViewModel.isRefreshing }
        else -> { false }
    }
        timetableViewModel.isRefreshing


    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = {
            when(navigationViewModel.currentScreen.Screen) {
                ScreenList.TIMETABLE_SCREEN -> {
                    CoroutineScope(Dispatchers.IO).launch {
                        Log.d("updateLog", "Pull refresh event")
                        timetableViewModel.update()
                    }
                }
                ScreenList.MAIN_SCREEN -> {
                    groupListViewModel.get()
                }
            }

        }
    )

    Box(
        modifier = if (
            navigationViewModel.currentScreen.Screen == ScreenList.TIMETABLE_SCREEN ||
            navigationViewModel.currentScreen.Screen == ScreenList.MAIN_SCREEN)
            Modifier.pullRefresh(pullRefreshState)
            else Modifier
    ) {
        Scaffold(
            modifier = Modifier
                .nestedScroll(
                    scrollBehavior.nestedScrollConnection
                ),
            topBar = {
                CollapsingAppBar(
                    mainViewModel = mainViewModel,
                    navigationViewModel = navigationViewModel
                ).run { Create(scrollBehavior) }
            },
            floatingActionButton = {
                FloatingMenu(
                    mainViewModel = mainViewModel,
                    navigationViewModel = navigationViewModel,
                    timetableViewModel = timetableViewModel
                ).run { Create() }
            }
        )
        {
            Frame(
                it,
                mainViewModel,
                timetableViewModel,
                groupListViewModel,
                navigationViewModel,
                settingsViewModel,
                updateDialog
            )
        }
        PullRefreshIndicator(
            isRefreshing,
            pullRefreshState,
            Modifier.align(Alignment.TopCenter),
            contentColor = MaterialTheme.colorScheme.primary
        )
    }
}

@ExperimentalAnimationApi
@Composable
fun Frame(
    paddingValues: PaddingValues,
    mainViewModel: MainViewModel,
    timetableViewModel: TimetableScreenVM,
    groupListViewModel: GroupListScreenVM,
    navigationViewModel: NavigationVM,
    settingsViewModel: SettingsScreenVM,
    updateDialog: UpdateInfo
) {
    Surface(
        modifier = Modifier
            .padding(paddingValues),
        color = MaterialTheme.colorScheme.background
    )
    {
        MaterialTheme {

            updateDialog.run {
                checkUpdate()
                UpdateInfoAlertDialog()
            }

           Navigation(
            mainViewModel,
            timetableViewModel,
            groupListViewModel,
            navigationViewModel,
            settingsViewModel
           )
        }
    }
}