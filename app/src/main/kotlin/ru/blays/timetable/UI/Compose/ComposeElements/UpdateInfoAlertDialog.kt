package ru.blays.timetable.UI.Compose.ComposeElements

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.blays.AppUpdater.UpdateChecker
import ru.blays.AppUpdater.dataClasses.UpdateInfoModel
import ru.blays.timetable.BuildConfig

@OptIn(ExperimentalMaterial3Api::class)
class UpdateInfo(private val context: ComponentActivity) {

    internal object Status {
        const val START_REQUEST = "START_REQUEST"
        const val START_DOWNLOAD = "START_DOWNLOAD"
        const val END_DOWNLOAD = "END_DOWNLOAD"
        const val ERROR = "ERROR"
    }

    private val updateChecker = UpdateChecker(context = context, BuildConfig.VERSION_CODE)

    private var isUpdateAccept by mutableStateOf(false)

    private var isUpdateDialogShow by mutableStateOf(false)

    private var downloadProgress by mutableStateOf(0f)

    private var updateInfo: UpdateInfoModel? = null

    private var downloadStatus: String? = null

    var isUpdateChecked = false

    init {

        updateChecker.updateInfo.observe(context) { updateInfo ->
            this.updateInfo = updateInfo
        }

        updateChecker.isUpdateAvailable.observe(context) { UpdateAvailable ->
            this.isUpdateDialogShow = UpdateAvailable
        }
    }


    fun checkUpdate() = CoroutineScope(Dispatchers.IO).launch {
        if (!isUpdateChecked) {
            updateChecker.check()
            isUpdateChecked = true
        }
    }

    @Composable
    fun UpdateInfoAlertDialog() {

        if (isUpdateDialogShow) {

            AlertDialog(
                onDismissRequest = { },
                modifier = Modifier
                    .wrapContentHeight()
            ) {

                Box(
                    modifier = Modifier
                        .background(
                            shape = MaterialTheme.shapes.medium,
                            color = MaterialTheme.colorScheme.background
                        ),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(10.dp)
                    ) {

                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = if (isUpdateAccept) "Загрузка обновления" else "Доступно обновление",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleLarge
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        if (isUpdateAccept) {

                            updateChecker.downloadStatus.observe(context) { status ->
                                downloadStatus = status
                            }

                            updateChecker.downloadProgress.observe(context) { progress ->
                                downloadProgress = progress
                            }

                            updateChecker.status.observe(context) { status ->
                                when (status) {
                                    Status.START_REQUEST -> {}
                                    Status.START_DOWNLOAD -> {}
                                    Status.END_DOWNLOAD -> { isUpdateDialogShow = false }
                                    Status.ERROR -> {}
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            LinearProgressIndicator(
                                modifier = Modifier.fillMaxWidth(),
                                progress = downloadProgress
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = "Прогресс: ${(downloadProgress * 100).toInt()}%",
                                textAlign = TextAlign.End,
                                modifier = Modifier.fillMaxWidth()
                            )

                        } else {

                            val state = rememberScrollState()

                            Column(
                                modifier = Modifier
                                    .fillMaxHeight(0.8F)
                                    .fillMaxWidth()
                                    .verticalScroll(state)
                            ) {

                                val textStyle = MaterialTheme.typography.titleMedium

                                Text (text = "Имя версии: ${updateInfo?.versionName}", style = textStyle)
                                Spacer(modifier = Modifier.height(4.dp))

                                Text(text = "Код версии: ${updateInfo?.versionCode}", style = textStyle)
                                Spacer(modifier = Modifier.height(4.dp))

                                if (updateInfo?.changed!!.isNotEmpty()) {
                                    Text(text = "Изменено:", style = textStyle)
                                    for (text in updateInfo?.changed!!) {
                                        Text(text = "• $text", modifier = Modifier.padding(vertical = 2.dp))
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                }

                                if (updateInfo?.added!!.isNotEmpty()) {
                                    Text(text = "Добавлено:", style = textStyle)
                                    for (text in updateInfo?.added!!) {
                                        Text(text = "• $text", modifier = Modifier.padding(vertical = 2.dp))
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                }

                                if (updateInfo?.deleted!!.isNotEmpty()) {
                                    Text(text = "Удалено:", style = textStyle)
                                    for (text in updateInfo?.deleted!!) {
                                        Text(text = "• $text", modifier = Modifier.padding(vertical = 2.dp))
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            if (!isUpdateAccept) Button(onClick = { isUpdateDialogShow = false }) {
                                Text(text = "Отмена")
                            }
                            if (!isUpdateAccept) Button(onClick = {
                                isUpdateAccept = true
                                updateChecker.downloadUpdate()
                            }
                            ) {
                                Text(text = "Установить")
                            }
                        }
                    }
                }
            }
        }
    }
}
