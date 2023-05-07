package ru.blays.timetable.UI.Screens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.pullrefresh.PullRefreshIndicator
import androidx.compose.material3.pullrefresh.pullRefresh
import androidx.compose.material3.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency
import com.ramcosta.composedestinations.navigation.navigate
import com.theapache64.rebugger.Rebugger
import ru.blays.timetable.UI.Compose.ComposeElements.CollapsingAppBar
import ru.blays.timetable.UI.Compose.ComposeElements.FloatingBottomMenu.BottomBarItem
import ru.blays.timetable.UI.Compose.ComposeElements.FloatingBottomMenu.FloatingBottomBar
import ru.blays.timetable.UI.Compose.ComposeElements.UpdateInfo
import ru.blays.timetable.UI.Compose.Root.MainViewModel
import ru.blays.timetable.UI.Compose.Screens.NavGraphs
import ru.blays.timetable.UI.Compose.Screens.SettingsScreen.SettingsScreenVM
import ru.blays.timetable.UI.Compose.Screens.SimpleListScreen.SimpleListScreenVM
import ru.blays.timetable.UI.Compose.Screens.StatisticScreen.StatisticScreenViewModel
import ru.blays.timetable.UI.Compose.Screens.TimeTableScreen.TimetableScreenVM
import ru.blays.timetable.UI.Compose.Screens.destinations.AboutScreenDestination
import ru.blays.timetable.UI.Compose.Screens.destinations.MainScreenDestination
import ru.blays.timetable.UI.Compose.Screens.destinations.SettingsScreenDestination
import ru.blays.timetable.UI.Compose.Screens.destinations.TimetableScreenDestination
import ru.hh.toolbar.custom_toolbar.rememberToolbarScrollBehavior

@ExperimentalAnimationApi
@Composable
fun RootElements(
    mainViewModel: MainViewModel,
    timetableViewModel: TimetableScreenVM,
    groupListViewModel: SimpleListScreenVM,
    settingsViewModel: SettingsScreenVM,
    statisticScreenViewModel: StatisticScreenViewModel,
    updateDialog: UpdateInfo,
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

    val shouldHideNavigationBar = mainViewModel.isFloatingMenuVisible

    val navBarHeightDp = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

    val navOffset by animateDpAsState(if (shouldHideNavigationBar) 80.dp + navBarHeightDp else 0.dp)

    val emptyWindowInsets = WindowInsets(0.dp)

    val navOptions: NavOptionsBuilder.() -> Unit = {
        restoreState = true
        launchSingleTop = true
    }

    val iconPath = Icons.Rounded

    val itemsList = listOf(
        BottomBarItem.Icon(icon = iconPath.Home, id = "home") {
            mainViewModel.screenID = 0
            navigationController.navigate(
                direction = MainScreenDestination,
                navOptionsBuilder = navOptions
            )
        },
        BottomBarItem.Icon(icon = iconPath.Star, id = "favorite") {
            mainViewModel.screenID = 1
            if (mainViewModel.favoriteHref != "no" && mainViewModel.favoriteHref != null && mainViewModel.favoriteSource != null) {
                navigationController.navigate(
                    route = TimetableScreenDestination(
                        href = mainViewModel.favoriteHref!!,
                        source = mainViewModel.favoriteSource!!
                    ).route
                )
            }
        },
        BottomBarItem.Icon(icon = iconPath.Settings, id = "settings") {
            mainViewModel.screenID = 2
            navigationController.navigate(
                direction = SettingsScreenDestination,
                navOptionsBuilder = navOptions
            )
        },
        BottomBarItem.Icon(icon = iconPath.Info, id = "about") {
            mainViewModel.screenID = 3
            navigationController.navigate(
                direction = AboutScreenDestination,
                navOptionsBuilder = navOptions
            )
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
            bottomBar = {

                FloatingBottomBar(
                    expanded = false,
                    selectedItem = mainViewModel.screenID,
                    items = itemsList,
                    modifier = Modifier.offset {
                    IntOffset(
                        0,
                        navOffset.toPx().toInt()
                    )
                }) {}
            }, contentWindowInsets = emptyWindowInsets
        ) { padding ->

            Surface(
                modifier = Modifier
                    .padding(top = padding.calculateTopPadding()),
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
                        dependency(statisticScreenViewModel)
                    }
                )

                if (mainViewModel.isInit && mainViewModel.openFavoriteOnAppStart && mainViewModel.favoriteHref != null && mainViewModel.favoriteHref != "no") {
                    navigationController.navigate(
                        route = TimetableScreenDestination(
                            href = mainViewModel.favoriteHref!!,
                            source = mainViewModel.favoriteSource!!
                        ).route
                    )
                    mainViewModel.screenID = 1
                    mainViewModel.isInit = false
                }
            }
        }
        PullRefreshIndicator(
            isRefreshing,
            pullRefreshState,
            Modifier.align(Alignment.TopCenter),
            contentColor = MaterialTheme.colorScheme.primary
        )
    }

    Rebugger(
        trackMap = mapOf(
            "isRefreshing" to isRefreshing,
            "pullRefreshState" to pullRefreshState,
            "titleText" to mainViewModel.titleText,
            "FloatingMenuVisibility" to mainViewModel.isFloatingMenuVisible
        )
    )
}