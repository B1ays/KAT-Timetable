package ru.blays.timetable.UI.ComposeElements

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.blays.timetable.UI.Compose.ComposeElements.navigation.NavigationVM
import ru.blays.timetable.UI.Compose.Screens.TimeTableScreen.TimetableScreenVM
import ru.blays.timetable.UI.DataClasses.CardShape
import ru.blays.timetable.UI.DataClasses.DefaultPadding
import ru.blays.timetable.domain.models.GetDaysListModel
import ru.blays.timetable.domain.models.GetSubjectsListModel

class TimeTableScreen(private val timetableViewModel: TimetableScreenVM, private val navigationViewModel: NavigationVM) {

    @ExperimentalAnimationApi
    @Composable
    fun Create() {

        LaunchedEffect(key1 = true) {
            timetableViewModel.get(navigationViewModel.currentScreen.Key)
        }

        if (timetableViewModel.showProgress) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(36.dp),
                contentAlignment = Alignment.TopCenter
            ) { CircularProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth(0.4F),
                strokeWidth = 6.dp,
                strokeCap = StrokeCap.Round
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                val items = timetableViewModel.timetable.daysWithSubjectsList

                items(items) { days ->
                    TimeTableCard(list = days)
                }
            }
        }
    }

    @ExperimentalAnimationApi
    @Composable
    private fun TimeTableCard(list: GetDaysListModel) {

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
    private fun SubjectItem(subject: GetSubjectsListModel) {
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
}

