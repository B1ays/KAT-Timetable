@file:OptIn(ExperimentalMaterial3Api::class)

package ru.blays.timetable.Compose

import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.animation.*
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.blays.timetable.ObjectBox.Boxes.DaysInTimeTableBox
import ru.blays.timetable.ObjectBox.Boxes.GroupListBox
import ru.blays.timetable.ObjectBox.Boxes.GroupListBox_
import ru.blays.timetable.ObjectBox.Boxes.SubjectsListBox
import ru.blays.timetable.groupListBox
import ru.blays.timetable.objectBoxManager



@Composable
fun RootElements() {
    val titleText by remember { mutableStateOf("Главная") }
    Scaffold(topBar = {
        TopAppBar(title = { Text(text = titleText) })
    }
    ) {
        Frame(it)
    }
}

@Composable
fun Frame(paddingValues: PaddingValues) {
    var currentScreen by remember { mutableStateOf(ScreenList.main_screen) }
    var groupList by remember { mutableStateOf(listOf<GroupListBox>()) }
    val onBack = { if (currentScreen != ScreenList.main_screen) currentScreen = ScreenList.main_screen }
    groupList = objectBoxManager.getGroupListFromBox()!!
    Surface(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        MaterialTheme {
            when(currentScreen) {
                ScreenList.main_screen -> {
                    BackPressHandler(onBackPressed = onBack)
                    SimpleList(list = groupList,
                    Modifier.clickable { currentScreen = ScreenList.timetable_screen })
                }
                ScreenList.timetable_screen -> {
                    BackPressHandler(onBackPressed = onBack)
                    val daysList = groupListBox.query(GroupListBox_.href.equal("cg60.htm")).build().find()
                    TimeTableView(daysList[0])
                }
                ScreenList.favoriteTimeTable_screen -> {
                    Text(text = "Test favorite")
                }
            }
        }
    }
}

@Composable
fun SimpleList(list: List<GroupListBox>, modifier: Modifier) {
    LazyColumn{
        itemsIndexed(list) {_, item ->
            SimpleCard(title = item, modifier)
        }
    }
}

@Composable
fun SimpleCard(title: GroupListBox, modifier: Modifier) {
    val visibilityState = remember {
        MutableTransitionState(false).apply {
            targetState = true
        }
    }

    AnimatedVisibility(
        visibleState =  visibilityState,
        enter = slideInHorizontally() + scaleIn(),
        exit = slideOutHorizontally()
    ) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 5.dp),
            shape = RoundedCornerShape(10.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                text = title.groupCode,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }

    }

@Composable
fun TimeTableView(list: GroupListBox) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(list.days) {
            TimeTableCard(list = it)
        }
    }
}

@Composable
fun TimeTableCard(list: DaysInTimeTableBox) {
    val visibilityState = remember {
        MutableTransitionState(false).apply {
            targetState = true
        }
    }

    AnimatedVisibility(
        visibleState =  visibilityState,
        enter = slideInHorizontally() + scaleIn(),
        exit = slideOutHorizontally()
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 5.dp),
            shape = RoundedCornerShape(10.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        )
        {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            )
            {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = list.day,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp
                )
                for (subject in list.subjects) {
                    SubjectItem(subject)
                }
            }
        }
    }
}

@Composable
fun SubjectItem(subject: SubjectsListBox) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults
            .cardElevation(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.background)
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Text(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(5.dp),
                text = subject.position,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.background)
            Column(modifier = Modifier
                .fillMaxHeight()
                .padding(start = 10.dp)) {
                Text(text = subject.subject, textAlign = TextAlign.Center)
                Row(modifier = Modifier.padding(horizontal = 4.dp)) {
                    Text(modifier = Modifier.fillMaxWidth(0.5F), text = subject.lecturer, textAlign = TextAlign.Start)
                    Text(modifier = Modifier.fillMaxWidth(), text = subject.auditory, textAlign = TextAlign.End)
                }
            }
        }
    }
}


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