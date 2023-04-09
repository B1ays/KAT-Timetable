package ru.blays.timetable.Compose.ComposeElements

import androidx.compose.animation.*
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.blays.timetable.Compose.States.ScreenState
import ru.blays.timetable.Compose.helperClasses.CardShape
import ru.blays.timetable.Compose.helperClasses.CurrentTimeTable
import ru.blays.timetable.Compose.helperClasses.CurrentTimeTable.daysList
import ru.blays.timetable.Compose.helperClasses.DefaultPadding
import ru.blays.timetable.ObjectBox.Boxes.DaysInTimeTableBox
import ru.blays.timetable.ObjectBox.Boxes.SubjectsListBox

@ExperimentalAnimationApi
@Composable
fun TimeTableView() {

    CurrentTimeTable.getTimeTable(ScreenState.currentScreen.Key)

    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(daysList) {
            TimeTableCard(list = it)
        }
    }
}

@ExperimentalAnimationApi
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
                .padding(
                    horizontal = DefaultPadding.CardHorizontalPadding,
                    vertical = DefaultPadding.CardVerticalPadding
                ),
            shape = CardShape.CardStandalone,
            elevation = CardDefaults.cardElevation(4.dp)
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
                        .fillMaxWidth()
                        .padding(bottom = 4.dp),
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
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = if (subject.subgroups == "1") Alignment.CenterStart else Alignment.CenterEnd,
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(if (subject.subgroups == "BOTH") 1F else 0.9F)
                .padding(horizontal = 6.dp, vertical = 4.dp),
            shape = CardShape.CardStandalone,
            elevation = CardDefaults
                .cardElevation(2.dp)
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
                        .border(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(20.dp)
                        )
                        .padding(5.dp),
                    text = subject.position,
                    textAlign = TextAlign.Center
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 10.dp)
                )
                {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = subject.subject,
                        textAlign = TextAlign.Center
                    )
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                    )
                    {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth(0.7F),
                            text = subject.lecturer,
                            textAlign = TextAlign.Start
                        )
                        Text(
                            modifier = Modifier
                                .fillMaxWidth(),
                            text = subject.auditory,
                            textAlign = TextAlign.End
                        )
                    }
                }
            }
        }
    }
}