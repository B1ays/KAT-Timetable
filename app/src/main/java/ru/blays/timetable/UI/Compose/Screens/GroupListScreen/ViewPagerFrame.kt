@file:OptIn(ExperimentalAnimationApi::class)

package ru.blays.timetable.UI.Compose.Screens.GroupListScreen

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.theapache64.rebugger.Rebugger
import ru.blays.timetable.UI.Compose.ComposeElements.navigation.NavigationVM
import ru.blays.timetable.UI.ComposeElements.SimpleListScreen

@OptIn(ExperimentalFoundationApi::class)
class ViewPagerFrame(private val simpleListScreenVM: SimpleListScreenVM, private val navigationViewModel: NavigationVM) {

    @Composable
    fun Create() {


        HorizontalPager(
            modifier = Modifier
                .fillMaxSize(),
            state = simpleListScreenVM.pagerState,
            pageCount = 3,
            userScrollEnabled = true
        ) { page ->
            SimpleListScreen(simpleListScreenVM = simpleListScreenVM, navigationViewModel = navigationViewModel).Create(page)
        }

        Rebugger(
            composableName = "ViewPagerFrame",
            trackMap = mapOf(
                "pageState" to simpleListScreenVM.pagerState
            )
        )
    }
}