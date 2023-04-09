package ru.blays.timetable.Compose.Screens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import ru.blays.timetable.Compose.ComposeElements.CollapsingAppBar
import ru.blays.timetable.Compose.ComposeElements.CustomAlertDialog
import ru.blays.timetable.Compose.ComposeElements.FloatingMenu
import ru.blays.timetable.Compose.ComposeElements.Navigation
import ru.blays.timetable.Compose.States.AlertDialogState
import ru.blays.timetable.Compose.States.AppBarState
import ru.blays.timetable.R
import ru.hh.toolbar.custom_toolbar.rememberToolbarScrollBehavior

@ExperimentalAnimationApi
@Composable
fun RootElements() {

    val defaultTitle = stringResource(id = R.string.Toolbar_MainScreen_title)
    AppBarState.changeTitleText(defaultTitle)
    val scrollBehavior = rememberToolbarScrollBehavior()

    Scaffold(
    modifier = Modifier
        .nestedScroll(scrollBehavior.nestedScrollConnection),
    topBar = {
        CollapsingAppBar(scrollBehavior)
    },
    floatingActionButton = { FloatingMenu() }
    )
    {
        Frame(it)
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
            if(AlertDialogState.isOpen) {
                CustomAlertDialog(message = AlertDialogState.text)
            }
           Navigation()
        }
    }
}