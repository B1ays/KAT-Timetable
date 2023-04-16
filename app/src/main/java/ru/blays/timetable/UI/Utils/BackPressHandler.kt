package ru.blays.timetable.UI.ComposeElements

import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import ru.blays.timetable.UI.ScreenData
import ru.blays.timetable.UI.ScreenList
import ru.blays.timetable.UI.Compose.navigationViewModel

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
    val backStack = navigationViewModel.backStack
    if (backStack.count() > 1) {
        navigationViewModel.changeScreen(backStack[backStack.lastIndex-1])
        backStack.removeLast()
    } else {
        navigationViewModel.changeScreen(ScreenData(ScreenList.main_screen))
    }
}
