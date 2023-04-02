package ru.blays.timetable.Compose.ComposeElements

import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.runtime.*
import ru.blays.timetable.Compose.ScreenData
import ru.blays.timetable.Compose.ScreenList
import ru.blays.timetable.Compose.States.BackStackState
import ru.blays.timetable.Compose.States.ScreenState

@Composable
fun BackPressHandler(
    backPressedDispatcher: OnBackPressedDispatcher? =
        LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher,
    onBackPressed: () -> Unit
) {
    val currentOnBackPressed by rememberUpdatedState(newValue = onBackPressed)

    val backCallback = remember {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                currentOnBackPressed()
            }
        }
    }

    DisposableEffect(key1 = backPressedDispatcher) {
        backPressedDispatcher?.addCallback(backCallback)

        onDispose {
            backCallback.remove()
        }
    }
}

val onBack: () -> Unit = {
    if (BackStackState.backStack.count() > 1) {
        ScreenState.changeScreen(BackStackState.backStack[BackStackState.backStack.lastIndex-1])
        BackStackState.backStack.removeLast()
    } else {
        ScreenState.changeScreen(ScreenData(ScreenList.main_screen))
    }
}