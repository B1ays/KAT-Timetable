@file:OptIn(ExperimentalMaterial3Api::class)

package ru.blays.timetable.Compose.ComposeElements

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.blays.timetable.Compose.States.AppBarState
import ru.blays.timetable.R

@ExperimentalAnimationApi
@Composable
fun RootElements(
    mainDbState: Boolean
) {
    val defaultTitle = stringResource(id = R.string.Toolbar_MainScreen_title)

    AppBarState.changeTitleText(defaultTitle)

    if (mainDbState) {

        Scaffold(topBar = {
            TopAppBar(title = { Text(text = AppBarState.titleText) })
        },
        floatingActionButton = { FloatingMenu() }
        )
        {
            Frame(
                it
            )
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun Frame(
    paddingValues: PaddingValues
) {
    Surface(
        modifier = Modifier
            .padding(paddingValues),
        color = MaterialTheme.colorScheme.background
    )
    {
        MaterialTheme {
           Navigation()
        }
    }
}