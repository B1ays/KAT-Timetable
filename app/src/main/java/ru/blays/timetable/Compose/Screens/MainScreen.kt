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
import ru.blays.timetable.R

@ExperimentalAnimationApi
@Composable
fun RootElements(
    mainDbState: Boolean
) {
    val defaultTitle = stringResource(id = R.string.Toolbar_MainScreen_title)
    var titleText by remember { mutableStateOf(defaultTitle) }
    var currentScreen by remember { mutableStateOf(ScreenData(ScreenList.main_screen, "")) }


    val onScreenChange: (ScreenData) -> Unit = { screen ->
        currentScreen = screen
    }
    if (mainDbState) {
        Scaffold(topBar = {
            TopAppBar(title = { Text(text = titleText) })
        },
        floatingActionButton = { FloatingMenu(onScreenChange, currentScreen) }
        )
        {
            Frame(
                it,
                onTitleChange = { title -> titleText = title },
                currentScreen,
                onScreenChange
            )
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun Frame(
    paddingValues: PaddingValues,
    onTitleChange: (String) -> Unit,
    currentScreen: ScreenData,
    onScreenChange: (ScreenData) -> Unit
) {
    Surface(
        modifier = Modifier
            .padding(paddingValues),
        color = MaterialTheme.colorScheme.background
    )
    {
        MaterialTheme {
           Navigation(
                onTitleChange,
                currentScreen,
                onScreenChange)
        }
    }
}