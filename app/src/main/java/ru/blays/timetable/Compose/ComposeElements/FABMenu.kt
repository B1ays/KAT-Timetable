package ru.blays.timetable.Compose.ComposeElements

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.blays.timetable.Compose.ScreenData
import ru.blays.timetable.Compose.ScreenList
import ru.blays.timetable.Compose.Utils.FloatingMenuActions
import ru.blays.timetable.Compose.Utils.FloatingMenuActionsModel
import ru.blays.timetable.Compose.Utils.FloatingMenuItems
import ru.blays.timetable.Compose.Utils.FloatingMenuItemsModel

@ExperimentalAnimationApi
@Composable
fun FloatingMenu(onScreenChange: (ScreenData) -> Unit, currentScreen: ScreenData) {
    var isExpanded by remember { mutableStateOf(false) }

    val onExpandedChange = {
        isExpanded = !isExpanded
    }

    Box(
        modifier = Modifier
            .background(
                shape = RoundedCornerShape(10.dp),
                color = MaterialTheme.colorScheme.primary
            )
            .clickable(enabled = !isExpanded)
            {
                if (!isExpanded) {
                    isExpanded = true
                }
            }
    )
    {
        if (isExpanded) {

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
                    for (item in FloatingMenuItems.Items) {
                        FloatingMenuItem(item, onExpandedChange, onScreenChange)
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        FloatingMenuAction(
                            item = FloatingMenuActions.close,
                            onExpandedChange = onExpandedChange,
                            onScreenChange = onScreenChange,
                            currentScreen = currentScreen,
                            action = {
                                onExpandedChange()
                            }
                        )

                        if (currentScreen.Screen == ScreenList.timetable_screen) {
                            FloatingMenuAction(
                                item = FloatingMenuActions.refresh,
                                onExpandedChange = onExpandedChange,
                                onScreenChange = onScreenChange,
                                currentScreen = currentScreen,
                                action = {
                                    onScreenChange(ScreenData(ScreenList.update_TimeTable, currentScreen.Key))
                                }
                            )
                        }
                    }
                }
            }
        } else
        {
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
fun FloatingMenuItem(
    item: FloatingMenuItemsModel,
    onExpandedChange: () -> Unit,
    onScreenChange: (ScreenData) -> Unit
) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 3.dp),
        shape = RoundedCornerShape(4.dp)
    )
    {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(7.dp)
            .clickable {
                onExpandedChange()
                onScreenChange(ScreenData(item.destinationScreen, "cg60.htm"))
            }
        )
        {
            Icon(
                imageVector = item.icon,
                contentDescription = "Settings button",
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
fun FloatingMenuAction(
    item: FloatingMenuActionsModel,
    onExpandedChange: () -> Unit,
    onScreenChange: (ScreenData) -> Unit,
    currentScreen: ScreenData,
    action: () -> Unit
) {
    IconButton(
        onClick = {
           action()
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