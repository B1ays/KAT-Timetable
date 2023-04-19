package ru.blays.timetable.UI.Compose.ComposeElements

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.blays.timetable.UI.Compose.ComposeElements.navigation.NavigationVM
import ru.blays.timetable.UI.Compose.MainViewModel
import ru.blays.timetable.UI.Compose.Screens.TimeTableScreen.TimetableScreenVM
import ru.blays.timetable.UI.ScreenData
import ru.blays.timetable.UI.ScreenList

class FloatingMenu(
    private val mainViewModel : MainViewModel,
    private val navigationViewModel : NavigationVM,
    private val timetableViewModel : TimetableScreenVM
) {
    @ExperimentalAnimationApi
    @Composable
    fun Create() {

        val floatingMenuItems = FloatingMenuItems()
        val floatingMenuActions = FloatingMenuActions()

        Box(
            modifier = Modifier
                .background(
                    shape = RoundedCornerShape(10.dp),
                    color = MaterialTheme.colorScheme.primary
                )
                .clickable(enabled = !mainViewModel.isMenuExpanded)
                {
                    mainViewModel.isMenuExpanded = !mainViewModel.isMenuExpanded
                }
                .animateContentSize(
                    animationSpec = tween(
                        durationMillis = 200,
                        easing = FastOutLinearInEasing
                    )
                )
        )
        {
            if (mainViewModel.isMenuExpanded) {

                // Развёрнутое меню

                val visibilityState = remember {
                    MutableTransitionState(false).apply {
                        targetState = true
                    }
                }

                AnimatedVisibility(
                    visibleState = visibilityState,
                    enter = scaleIn(),
                    exit = scaleOut()
                ) {
                    Column(
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .padding(horizontal = 10.dp)
                            .fillMaxWidth(0.7f)
                    )
                    {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 6.dp),
                            text = "Меню",
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.background,
                            style = MaterialTheme.typography.titleLarge
                        )
                        for (item in floatingMenuItems.Items) {
                            FloatingMenuItem(item)
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {

                            if (navigationViewModel.currentScreen.Screen == ScreenList.TIMETABLE_SCREEN) FloatingMenuAction(item = floatingMenuActions.refresh)
                            FloatingMenuAction(item = floatingMenuActions.close)
                        }
                    }
                }
            }
            // Свёрнутое меню
            else {
                Icon(
                    modifier = Modifier
                        .padding(14.dp),
                    imageVector = Icons.Rounded.Menu,
                    contentDescription = "Menu button",
                    tint = MaterialTheme.colorScheme.background
                )
            }
        }
    }

    @Composable
    private fun FloatingMenuItem(
        item: FloatingMenuItemsModel
    ) {
        Card(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp)
            .clickable {
                mainViewModel.isMenuExpanded = !mainViewModel.isMenuExpanded
                item.action()
            },
            shape = RoundedCornerShape(4.dp)
        )
        {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(7.dp)
            )
            {
                Icon(
                    imageVector = item.icon,
                    contentDescription = "Menu item",
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    modifier = Modifier
                        .padding(start = 6.dp),
                    text = item.title,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }

    @Composable
    private fun FloatingMenuAction(
        item: FloatingMenuActionsModel
    ) {
        IconButton(
            onClick = {
                item.action()
            }
        )
        {
            Icon(
                modifier = Modifier
                    .size(40.dp)
                    .padding(horizontal = 4.dp),
                imageVector = item.icon,
                contentDescription = "Close button",
                tint = MaterialTheme.colorScheme.background
            )
        }
    }

    data class FloatingMenuItemsModel(
        val title: String,
        val icon: ImageVector,
        val destinationScreen: String,
        val action: () -> Unit
    )

    data class FloatingMenuActionsModel(
        val icon: ImageVector,
        val action: () -> Unit
    )

    inner class FloatingMenuItems {
        private val iconPath = androidx.compose.material.icons.Icons.Rounded
        val Items = listOf(
            FloatingMenuItemsModel(title = "Настройки", iconPath.Settings, ScreenList.SETTINGS_SCREEN) {
                navigationViewModel.changeScreen(ScreenData(ScreenList.SETTINGS_SCREEN))
            },
            FloatingMenuItemsModel(title = "Избранное", iconPath.Star, ScreenList.TIMETABLE_SCREEN) {
                if (mainViewModel.favoriteHref != "no" && mainViewModel.favoriteHref != null) {
                    navigationViewModel.changeScreen(
                        ScreenData(
                            ScreenList.TIMETABLE_SCREEN,
                            mainViewModel.favoriteHref!!
                        )
                    )
                }
            },
            FloatingMenuItemsModel(title = "О приложении", iconPath.Info, ScreenList.ABOUT_SCREEN) {
                navigationViewModel.changeScreen(ScreenData(ScreenList.ABOUT_SCREEN))
            }
        )
    }

    inner class FloatingMenuActions {
        val refresh = FloatingMenuActionsModel(Icons.Rounded.Refresh) {
            CoroutineScope(context = Dispatchers.IO).launch { timetableViewModel.update() }
        }
        val close = FloatingMenuActionsModel(Icons.Rounded.Close) {
            mainViewModel.isMenuExpanded = !mainViewModel.isMenuExpanded
        }
    }
}