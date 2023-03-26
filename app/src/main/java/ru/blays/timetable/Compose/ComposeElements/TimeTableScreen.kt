package ru.blays.timetable.Compose.ComposeElements

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.blays.timetable.ObjectBox.Boxes.DaysInTimeTableBox
import ru.blays.timetable.ObjectBox.Boxes.SubjectsListBox
import ru.blays.timetable.htmlParser
import ru.blays.timetable.objectBoxManager

@Composable
fun TimeTableView(href: String) {

    Log.d("TimetableLog", "Intialize List: Start")

    var daysList by remember { mutableStateOf(objectBoxManager.getDaysFromTable(href)[0].days) }

    if (daysList.isEmpty()) {
        Log.d("TimetableLog", "Intialize List: Get Days with href - $href - Start")
        LaunchedEffect(key1 = true ) {
            GlobalScope.launch {
                htmlParser.getTimeTable(href)
                daysList = objectBoxManager.getDaysFromTable(href)[0].days
                Log.d("TimetableLog", "Intialize List: Get Days with href - $href - End")
            }
        }
    }

    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(daysList) {
            TimeTableCard(list = it)
        }
    }
    Log.d("TimetableLog", "Intialize List: End")
}

@Composable
fun TimeTableCard(list: DaysInTimeTableBox) {

    Log.d("TimetableLog", "Intialize Card: Start")

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
    Log.d("TimetableLog", "Intialize List: End")
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
                    .fillMaxSize()
                    .padding(start = 10.dp))
            {
                Text(modifier = Modifier.fillMaxWidth(),
                    text = subject.subject,
                    textAlign = TextAlign.Center)
                Row(modifier =
                Modifier
                    .padding(horizontal = 4.dp))
                {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(0.5F),
                        text = subject.lecturer,
                        textAlign = TextAlign.Start)
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = subject.auditory,
                        textAlign = TextAlign.End)
                }
            }
        }
    }
}