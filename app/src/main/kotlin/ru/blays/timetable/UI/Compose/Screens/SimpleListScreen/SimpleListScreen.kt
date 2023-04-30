package ru.blays.timetable.UI.ComposeElements

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ramcosta.composedestinations.navigation.navigate
import com.theapache64.rebugger.Rebugger
import ru.blays.timetable.UI.Compose.Screens.SimpleListScreen.SimpleListScreenVM
import ru.blays.timetable.UI.DataClasses.CardShape
import ru.blays.timetable.UI.DataClasses.DefaultPadding
import ru.blays.timetable.UI.destinations.TimetableScreenDestination
import ru.blays.timetable.domain.models.GetSimpleListModel


class SimpleListScreen(
    private val navigation: NavController,
    private val simpleListScreenVM: SimpleListScreenVM
   ) {

    object ListDataType {
        const val LECTURER = 0
        const val GROUPS = 1
        const val AUDITORY = 2
    }

    @ExperimentalAnimationApi
    @Composable
    fun Create(screenType: Int) {

        simpleListScreenVM.get()

        val list = when (screenType) {
            ListDataType.GROUPS -> simpleListScreenVM.listGroups
            ListDataType.LECTURER -> simpleListScreenVM.listLecturer
            ListDataType.AUDITORY -> simpleListScreenVM.listAuditory
            else -> simpleListScreenVM.listGroups
        }

        LazyColumn{
            itemsIndexed(list) { _, item ->
                SimpleCard(getGroupListModel = item, screenType = screenType)
            }
        }
        Rebugger(composableName = "SimpleListScreen",
            trackMap = mapOf(
            "list" to list,
            "screenType" to screenType
        ))
    }

    @ExperimentalAnimationApi
    @Composable
    private fun SimpleCard(
        getGroupListModel: GetSimpleListModel,
        screenType: Int
    ) {

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
                    )
                    .clickable {
                       navigation.navigate(TimetableScreenDestination(href= getGroupListModel.href ?: "", source = screenType))
                    },
                shape = CardShape.CardStandalone,
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Text(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    text = getGroupListModel.name ?: "",
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}