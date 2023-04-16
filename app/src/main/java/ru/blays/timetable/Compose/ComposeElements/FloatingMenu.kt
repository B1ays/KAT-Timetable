package ru.blays.timetable.Compose.ComposeElements

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
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.blays.timetable.Compose.HelperClasses.FloatingMenuActions
import ru.blays.timetable.Compose.HelperClasses.FloatingMenuActionsModel
import ru.blays.timetable.Compose.HelperClasses.FloatingMenuItems
import ru.blays.timetable.Compose.HelperClasses.FloatingMenuItemsModel
import ru.blays.timetable.Compose.mainViewModel


@ExperimentalAnimationApi
@Composable
fun FloatingMenu() {
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
                    for (item in FloatingMenuItems.Items) {
                        FloatingMenuItem(item)
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {

                        /*if (currentScreen.Screen == ScreenList.timetable_screen) FloatingMenuAction(item = FloatingMenuActions.refresh)*/
                        FloatingMenuAction(item = FloatingMenuActions.close)
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
fun FloatingMenuItem(
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
fun FloatingMenuAction(
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
