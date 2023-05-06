@file:OptIn(ExperimentalAnimationApi::class)

package ru.blays.timetable.UI.Compose.Screens.SimpleListScreen

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.theapache64.rebugger.Rebugger
import ru.blays.timetable.UI.Compose.Root.MainViewModel

@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
@RootNavGraph(start = true)
@Destination(route = "MAIN_SCREEN")
@Composable
fun MainScreen(
    navigation: NavController,
    simpleListScreenVM: SimpleListScreenVM,
    mainViewModel: MainViewModel
) {

    val pagerState = simpleListScreenVM.pagerState

    var titleText by remember { mutableStateOf("Группы") }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.settledPage }.collect { page ->
            when (page) {
                0 -> titleText = "Преподаватели"
                1 -> titleText = "Группы"
                2 -> titleText = "Аудитории"
            }
        }
    }

    mainViewModel.setParameterForScreen(
        screenType = "MAIN_SCREEN",
        titleText = titleText,
        pullRefreshAction = { simpleListScreenVM.get() },
        isRefreshing = simpleListScreenVM.isRefreshing
    )
    
    val page = SimpleListScreen(simpleListScreenVM = simpleListScreenVM, navigation = navigation, mainViewModel = mainViewModel)

    HorizontalPager(
        modifier = Modifier
            .fillMaxSize(),
        state = pagerState,
        pageCount = 3,
        userScrollEnabled = true
    ) { pageNumber ->
        Log.d("pagerLog", pageNumber.toString())
        page.Create(screenType = pageNumber)
    }

    Rebugger(
        composableName = "ViewPagerFrame",
        trackMap = mapOf(
            "pageState" to simpleListScreenVM.pagerState
        )
    )
}
