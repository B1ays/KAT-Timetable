@file:OptIn(ExperimentalTextApi::class)

package ru.blays.timetable.UI.Compose.Screens.StatisticScreen

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.blays.timetable.UI.Compose.Root.MainViewModel
import ru.blays.timetable.domain.models.ChartModel

@Destination(route = "STATISTICS_SCREEN")
@Composable
fun StatisticScreen(
    href: String,
    statisticScreenViewModel: StatisticScreenViewModel,
    mainViewModel: MainViewModel
) {

    mainViewModel.setParameterForScreen(
        screenType = "STATISTICS_SCREEN",
        titleText = "Итоги",
        pullRefreshAction = {
            CoroutineScope(Dispatchers.IO).launch {
                statisticScreenViewModel.get(href)
            }
        },
        isRefreshing = statisticScreenViewModel.isLoaded
    )

    LaunchedEffect(key1 = true) {
        statisticScreenViewModel.get(href)
    }


    if (!statisticScreenViewModel.isLoaded) {
        LazyColumn() {
            items(statisticScreenViewModel.list) { item ->
                ChartItem(item)
                /*Spacer(Modifier.height(20.dp))*/
                Divider(thickness = 2.dp, color = MaterialTheme.colorScheme.surfaceVariant)
            }
        }
    }
}

@Composable
private fun ChartItem(item: ChartModel) {

    val textMeasurer = rememberTextMeasurer()
    val textStyle = MaterialTheme.typography.labelSmall

    val chartBackgroundColor = MaterialTheme.colorScheme.surfaceVariant
    val chartLineColor = MaterialTheme.colorScheme.primary
    val chartDividerColor = MaterialTheme.colorScheme.outline

    val textColor = MaterialTheme.colorScheme.onBackground


    Column(modifier = Modifier
        .padding(12.dp)
        .fillMaxWidth()
    ) {

        Text(text = item.subject, style = MaterialTheme.typography.titleLarge)
        Row() {
            Text(text = "Преподаватель:", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.width(6.dp))
            Text(text = item.lecturer)
        }
        Row() {
            Text(text = "Всего часов:", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.width(6.dp))
            Text(text = item.totalHours)
        }
        Row() {
            Text(text = "Осталось часов:", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.width(6.dp))
            Text(text = item.hoursLeft)
        }

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
            .fillMaxWidth()
        ) {
            Canvas(
                modifier = Modifier
                    .fillMaxWidth(0.9F)
                    .height(20.dp)
                    .align(Alignment.Center)
            ) {
                val calculatedWidth = size.width * item.percent
                val chartLineSegmentWidth = (size.width) / 10

                Log.d("sizeLog", size.toString())

                drawLine(
                    color = chartBackgroundColor,
                    start = Offset.Zero,
                    end = Offset(size.width, 0F),
                    strokeWidth = size.height / 3,
                    cap = StrokeCap.Round
                )
                drawLine(
                    color = chartLineColor,
                    start = Offset.Zero,
                    end = Offset(calculatedWidth, 0F),
                    strokeWidth = size.height / 3,
                    cap = StrokeCap.Round
                )
                repeat(11) { iteration ->
                    drawLine(
                        color = chartDividerColor,
                        start = Offset(chartLineSegmentWidth * iteration, (size.height) / 3),
                        end = Offset(chartLineSegmentWidth * iteration, -(size.height) / 3),
                        strokeWidth = size.width / 150,
                        cap = StrokeCap.Round
                    )
                    val textResult = textMeasurer.measure(
                        text = "${iteration * 10}%",
                        style = textStyle
                    )
                    drawText(
                        textLayoutResult = textResult,
                        color = textColor,
                        topLeft = Offset(
                            chartLineSegmentWidth * iteration - textResult.size.width / 2,
                            size.height / 2.2F
                        ),
                    )
                }
            }
        }
    }
}