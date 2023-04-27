package ru.blays.timetable.UI.Screens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.pullrefresh.PullRefreshIndicator
import androidx.compose.material3.pullrefresh.pullRefresh
import androidx.compose.material3.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency
import com.ramcosta.composedestinations.navigation.navigate
import com.theapache64.rebugger.Rebugger
import ru.blays.timetable.UI.Compose.ComposeElements.CollapsingAppBar
import ru.blays.timetable.UI.Compose.ComposeElements.FloatingMenu
import ru.blays.timetable.UI.Compose.ComposeElements.UpdateInfo
import ru.blays.timetable.UI.Compose.Root.MainViewModel
import ru.blays.timetable.UI.Compose.Screens.SettingsScreen.SettingsScreenVM
import ru.blays.timetable.UI.Compose.Screens.SimpleListScreen.SimpleListScreenVM
import ru.blays.timetable.UI.Compose.Screens.TimeTableScreen.TimetableScreenVM
import ru.blays.timetable.UI.NavGraphs
import ru.blays.timetable.UI.destinations.TimetableScreenDestination
import ru.hh.toolbar.custom_toolbar.rememberToolbarScrollBehavior

@ExperimentalAnimationApi
@Composable
fun RootElements(
    mainViewModel: MainViewModel,
    timetableViewModel: TimetableScreenVM,
    groupListViewModel: SimpleListScreenVM,
    settingsViewModel: SettingsScreenVM,
    updateDialog: UpdateInfo
) {


    val scrollBehavior = rememberToolbarScrollBehavior()

    val navigationController = rememberNavController()

    val isPullRefreshAvailable = mainViewModel.isPullRefreshAvailable

    val isRefreshing = mainViewModel.isRefreshing

    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = {
            mainViewModel.refreshAction()
    }
)

    Box(
        modifier = if (isPullRefreshAvailable)
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
                    navigation = navigationController,
                    timetableViewModel = timetableViewModel
                ).Create(scrollBehavior)
            },
            floatingActionButton = {
                FloatingMenu(
                    mainViewModel = mainViewModel,
                    timetableViewModel = timetableViewModel,
                    navigation = navigationController
                ).Create()
            }
        )
        {
            Surface(
                modifier = Modifier
                    .padding(it),
                color = MaterialTheme.colorScheme.background
            ) {

                updateDialog.run {
                    checkUpdate()
                    UpdateInfoAlertDialog()
                }

                DestinationsNavHost(
                    navGraph = NavGraphs.root,
                    navController = navigationController,
                    dependenciesContainerBuilder = {
                        dependency(mainViewModel)
                        dependency(timetableViewModel)
                        dependency(groupListViewModel)
                        dependency(settingsViewModel)
                    }
                )

                if (mainViewModel.isInit && mainViewModel.openFavoriteOnAppStart && mainViewModel.favoriteHref != null && mainViewModel.favoriteHref != "no") {
                    navigationController.navigate(TimetableScreenDestination(href = mainViewModel.favoriteHref!!, source = mainViewModel.favoriteSource!!))
                }
                mainViewModel.isInit = false
            }

        }
        PullRefreshIndicator(
            isRefreshing,
            pullRefreshState,
            Modifier.align(Alignment.TopCenter),
            contentColor = MaterialTheme.colorScheme.primary
        )
    }

    Rebugger(trackMap = mapOf(
        "isRefreshing" to isRefreshing,
        "pullRefreshState" to pullRefreshState,
        "titleText" to mainViewModel.titleText,

    ))

}