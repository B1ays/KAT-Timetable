@file:OptIn(ExperimentalMaterial3Api::class)

package ru.blays.timetable.Compose.ComposeElements

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
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
    if (mainDbState) {
            Scaffold(topBar = {
                TopAppBar(title = { Text(text = titleText) })
            },
                floatingActionButton = { FloatingMenu() }
        )
        {
            Frame(
                it,
                onTitleChange = { title -> titleText = title }
            )
        }
    }
}

@Composable
fun Frame(paddingValues: PaddingValues, onTitleChange: (String) -> Unit) {
    var currentScreen by remember { mutableStateOf(ScreenData(ScreenList.main_screen, "")) }
    var groupList by remember { mutableStateOf(listOf<GroupListBox>()) }
    val onBack = { if (currentScreen.Screen != ScreenList.main_screen) currentScreen =
        ScreenData(ScreenList.main_screen, "")
    }
    groupList = objectBoxManager.getGroupListFromBox()!!

    Surface(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        MaterialTheme {
            when(currentScreen.Screen) {
                ScreenList.main_screen -> {
                    SimpleList(
                        list = groupList,
                        onOpenTimeTable = {
                            currentScreen = it
                        },
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
            }
        }
    }
}

@Preview
@Composable
fun FloatingMenu() {
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
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(0.7f),
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                    Text(
                        text = "Menu Title",
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.background
                    )
                    Text(
                        text = "FAB",
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.background
                    )
                    Text(
                        text = "FAB",
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.background
                    )
                    Text(
                        text = "FAB",
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.background
                    )
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



