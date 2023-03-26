@file:OptIn(ExperimentalMaterial3Api::class)

package ru.blays.timetable.Compose.ComposeElements

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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
        }
        ) {
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
    val onBack = { if (currentScreen.Screen != ScreenList.main_screen) currentScreen = ScreenData(
        ScreenList.main_screen, "")
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


