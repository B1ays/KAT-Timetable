package ru.blays.timetable.UI.Compose.Screens.TimeTableScreen

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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.theapache64.rebugger.Rebugger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.blays.timetable.R
import ru.blays.timetable.UI.Compose.Root.MainViewModel
import ru.blays.timetable.UI.DataClasses.CardShape
import ru.blays.timetable.UI.DataClasses.DefaultPadding
import ru.blays.timetable.UI.DataClasses.TimetableKey
import ru.blays.timetable.UI.Utils.timeLabelGenerator
import ru.blays.timetable.domain.models.GetDaysListModel
import ru.blays.timetable.domain.models.GetSubjectsListModel

@OptIn(ExperimentalAnimationApi::class)
@Destination(route = "TIMETABLE_SCREEN")
@Composable
fun TimetableScreen(timetableViewModel: TimetableScreenVM, mainViewModel: MainViewModel, href: String, source: Int) {

    mainViewModel.setParameterForScreen(
        screenType = "TIMETABLE_SCREEN",
        titleText = timetableViewModel.timetable.groupCode,
        subtitleText = timetableViewModel.timetable.updateDate,
        pullRefreshAction = { CoroutineScope(Dispatchers.IO).launch { timetableViewModel.update() }  },
        isRefreshing = timetableViewModel.isRefreshing
    )



    LaunchedEffect(key1 = true) {
        timetableViewModel.get(TimetableKey(source, href))
    }

    val items = timetableViewModel.timetable.daysWithSubjectsList

    if (!timetableViewModel.isRefreshing) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            items(items) { days ->
                TimeTableCard(list = days, viewModel = timetableViewModel)
            }
        }
        Rebugger(trackMap =
            mapOf(
                "list" to items,
                "isRefreshing" to timetableViewModel.isRefreshing,
            )
        )
    }


}


@ExperimentalAnimationApi
@Composable
private fun TimeTableCard(list: GetDaysListModel, viewModel: TimetableScreenVM) {

    val map = if (viewModel.showTimeLabel == true && viewModel.currentSource == 1) timeLabelGenerator(list.subjects) else emptyMap()

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
                    SubjectItem(subject, map[subject] ?: "", viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
private fun SubjectItem(
    subject: GetSubjectsListModel,
    timeLabel: String,
    viewModel: TimetableScreenVM
) {
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
                    if (viewModel.showTimeLabel == true) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .align(Alignment.CenterEnd)
                            ) {
                                Text(text = timeLabel)
                                Spacer(modifier = Modifier.width(6.dp))
                                Icon(imageVector = ImageVector.vectorResource(id = R.drawable.round_access_time_24), contentDescription = "timeLabelIco", tint = MaterialTheme.colorScheme.primary)
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = subject.title,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                    )
                    {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth(0.7F),
                            text = subject.subtitle1,
                            textAlign = TextAlign.Start
                        )
                        Text(
                            modifier = Modifier
                                .fillMaxWidth(),
                            text = subject.subtitle2,
                            textAlign = TextAlign.End
                        )
                    }
                }
            }
        }
    }
}


