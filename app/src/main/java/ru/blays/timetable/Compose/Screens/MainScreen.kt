@file:OptIn(ExperimentalMaterial3Api::class)

package ru.blays.timetable.Compose.ComposeElements

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.blays.timetable.Compose.ScreenData
import ru.blays.timetable.Compose.ScreenList
import ru.blays.timetable.Compose.Utils.AppBarState
import ru.blays.timetable.Compose.Utils.AppBarState.titleText
import ru.blays.timetable.R

@ExperimentalAnimationApi
@Composable
fun RootElements(
    mainDbState: Boolean
) {
    val defaultTitle = stringResource(id = R.string.Toolbar_MainScreen_title)
    var currentScreen by remember { mutableStateOf(ScreenData(ScreenList.main_screen, "")) }
    val backStack = mutableListOf(ScreenData(ScreenList.main_screen, ""))

    AppBarState.changeTitleText(defaultTitle)

    val onScreenChange: (ScreenData) -> Unit = { screen ->
        currentScreen = screen
    }
    if (mainDbState) {

        Scaffold(topBar = {
            TopAppBar(title = { Text(text = AppBarState.titleText) })
        },
        floatingActionButton = { FloatingMenu(onScreenChange, currentScreen) }
        )
        {
            Frame(
                it,
                currentScreen,
                onScreenChange,
                backStack
            )
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun Frame(
    paddingValues: PaddingValues,
    currentScreen: ScreenData,
    onScreenChange: (ScreenData) -> Unit,
    backStack: MutableList<ScreenData>
) {
    Surface(
        modifier = Modifier
            .padding(paddingValues),
        color = MaterialTheme.colorScheme.background
    )
    {
        MaterialTheme {
           Navigation(
                currentScreen,
                onScreenChange,
                backStack
           )
        }
    }
}