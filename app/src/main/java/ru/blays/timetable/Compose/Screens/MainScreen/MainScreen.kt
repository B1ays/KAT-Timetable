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
import ru.blays.timetable.Compose.ComposeElements.CollapsingAppBar
import ru.blays.timetable.Compose.ComposeElements.FloatingMenu
import ru.blays.timetable.Compose.ComposeElements.Navigation
import ru.hh.toolbar.custom_toolbar.rememberToolbarScrollBehavior

@ExperimentalAnimationApi
@Composable
fun RootElements() {

    val scrollBehavior = rememberToolbarScrollBehavior()

    Scaffold(
        modifier = Modifier
            .nestedScroll(
                scrollBehavior.nestedScrollConnection
            ),
        topBar = {
            CollapsingAppBar(scrollBehavior)
        },
        floatingActionButton = {
            FloatingMenu()
        }
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
        /*if(AlertDialogState.isOpen) {
                CustomAlertDialog(message = AlertDialogState.text)
            }*/
           Navigation()
        }
    }
}