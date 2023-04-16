package ru.blays.timetable.Compose.ComposeElements

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
import ru.blays.timetable.Compose.HelperClasses.CardShape
import ru.blays.timetable.Compose.HelperClasses.DefaultPadding
import ru.blays.timetable.Compose.ScreenData
import ru.blays.timetable.Compose.ScreenList
import ru.blays.timetable.Compose.groupListViewModel
import ru.blays.timetable.Compose.navigationVM
import ru.blays.timetable.domain.models.GetGroupListModel

@ExperimentalAnimationApi
@Composable
fun SimpleList() {

    LazyColumn{
        itemsIndexed(groupListViewModel.groupList) { _, item ->
            SimpleCard(getGroupListModel = item)
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun SimpleCard(
    getGroupListModel: GetGroupListModel
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
                     navigationVM.changeScreen(
                        ScreenData(
                            Screen = ScreenList.timetable_screen,
                            Key = getGroupListModel.href ?: ""
                        )
                    )
                },
            shape = CardShape.CardStandalone,
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                text = getGroupListModel.groupCode ?: "",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }

}