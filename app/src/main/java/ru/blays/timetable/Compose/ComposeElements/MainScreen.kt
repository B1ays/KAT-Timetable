@file:OptIn(ExperimentalMaterial3Api::class)

package ru.blays.timetable.Compose.ComposeElements

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.blays.timetable.Compose.ScreenData
import ru.blays.timetable.Compose.ScreenList
import ru.blays.timetable.ObjectBox.Boxes.GroupListBox
import ru.blays.timetable.R
import ru.blays.timetable.objectBoxManager

@Composable
fun RootElements(mainDbState: Boolean) {
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
        floatingActionButton = { FloatingMenu(onScreenChange) }
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
                onTitleChange = onTitleChange,
                currentScreen,
                onScreenChange)
        }
    }
}

@Composable
fun Navigation(
    onTitleChange: (String) -> Unit,
    currentScreen: ScreenData,
    onScreenChange: (ScreenData) -> Unit
) {
    var groupList by remember { mutableStateOf(listOf<GroupListBox>()) }

    val onBack = {
        if (currentScreen.Screen != ScreenList.main_screen) {
            onScreenChange(ScreenData(ScreenList.main_screen, ""))
        }
    }

    groupList = objectBoxManager.getGroupListFromBox()!!

    when(currentScreen.Screen) {
        ScreenList.main_screen -> {
            SimpleList(
                list = groupList,
                onScreenChange,
                onTitleChange
            )
            onTitleChange(stringResource(id = R.string.Toolbar_MainScreen_title))
        }
        ScreenList.timetable_screen -> {
            Log.d("ScreenCall", "Selected: ${currentScreen.Key}")
            BackPressHandler(onBackPressed = onBack)
            TimeTableView(currentScreen.Key)
        }
        ScreenList.favoriteTimeTable_screen -> {
            BackPressHandler(onBackPressed = onBack)
            Text(text = "Test favorite")
        }
        ScreenList.settings_screen -> {
            BackPressHandler(onBackPressed = onBack)
            SettingsScreen()
        }
    }
}

@Composable
fun FloatingMenu(onScreenChange: (ScreenData) -> Unit) {
    var isExpanded by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .background(
                shape = RoundedCornerShape(10.dp),
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            .clickable {
                isExpanded = !isExpanded
                Log.d("floatingButtonLog", "FloatingMenu clicked")
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
                enter = slideInHorizontally() + scaleIn(),
                exit = slideOutHorizontally()
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(0.7f)
                )
                {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        text = "Menu Title",
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.background,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            isExpanded = !isExpanded
                            onScreenChange(
                                ScreenData(
                                    ScreenList.settings_screen,
                                    ""
                                )
                            )
                        }
                    )
                    {
                        Icon(
                            imageVector = androidx.compose.material.icons.Icons.Rounded.Settings,
                            contentDescription = "Settings button",
                            tint = MaterialTheme.colorScheme.background
                        )
                        Text(
                            modifier = Modifier
                                .padding(start = 6.dp),
                            text = "Настройки",
                            color = MaterialTheme.colorScheme.background
                        )
                    }
                }
            }
        } else {
            Icon(
                modifier = Modifier
                    .padding(14.dp),
                imageVector = androidx.compose.material.icons.Icons.Rounded.Menu,
                contentDescription = "Menu button",
                tint = MaterialTheme.colorScheme.background
            )
        }
    }
}