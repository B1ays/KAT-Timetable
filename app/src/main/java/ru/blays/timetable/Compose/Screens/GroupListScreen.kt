package ru.blays.timetable.Compose.ComposeElements

import androidx.compose.animation.*
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.blays.timetable.Compose.ScreenData
import ru.blays.timetable.Compose.ScreenList
import ru.blays.timetable.ObjectBox.Boxes.GroupListBox

@ExperimentalAnimationApi
@Composable
fun SimpleList(
    list: List<GroupListBox>,
    onScreenChange: (ScreenData) -> Unit,
    onTitleChange: (String) -> Unit
) {
    LazyColumn{
        itemsIndexed(list) {_, item ->
            SimpleCard(title = item, onScreenChange, onTitleChange)
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun SimpleCard(
    title: GroupListBox,
    onScreenChange: (ScreenData) -> Unit,
    onTitleChange: (String) -> Unit
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
                .padding(horizontal = 10.dp, vertical = 5.dp)
                .clickable {
                    onTitleChange(
                        title.groupCode
                    )
                    onScreenChange(
                        ScreenData(
                            Screen = ScreenList.timetable_screen,
                            Key = title.href
                        )
                    )
                },
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