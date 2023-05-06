package ru.blays.timetable.UI.Compose.Screens.SettingsScreen

import android.os.Build
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import ru.blays.timetable.R
import ru.blays.timetable.UI.Compose.Root.MainViewModel
import ru.blays.timetable.UI.Compose.Screens.TimeTableScreen.TimetableScreenVM
import ru.blays.timetable.UI.DataClasses.AccentColorItem
import ru.blays.timetable.UI.DataClasses.AccentColorList
import ru.blays.timetable.UI.DataClasses.Animations.ModifierWithExpandAnimation
import ru.blays.timetable.UI.DataClasses.CardShape
import ru.blays.timetable.UI.DataClasses.DefaultPadding

@Destination(route = "SETTINGS_SCREEN")
@Composable
fun SettingsScreen(
    settingsViewModel: SettingsScreenVM,
    mainViewModel: MainViewModel,
    timetableViewModel: TimetableScreenVM
) {

    mainViewModel.setParameterForScreen(
        screenType = "SETTINGS_SCREEN",
        titleText = "Настройки"
    )

    val lazyColumnState = rememberLazyListState()

    mainViewModel.isFloatingMenuVisible = true

    LazyColumn(
        state = lazyColumnState,
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    )
    {
        item {
            ThemeSettings(
                settingsViewModel,
                mainViewModel
            )
        }
        item {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) MonetSettings(
                settingsViewModel,
                mainViewModel
            )
        }
        item {
            AccentSelector(
                settingsViewModel,
                mainViewModel
            )
        }
        item {
            FirstScreenSetting(settingsViewModel)
            TimeLabelSetting(
                settingsViewModel,
                timetableViewModel
            )
        }
    }
}

@Composable
private fun ThemeSettings(
    settingsViewModel: SettingsScreenVM,
    mainViewModel: MainViewModel
) {
    SettingsExpandableCard(title = "Тема приложения") {

        SettingsRadioButtonWithTitle(title = "Системная тема", state = settingsViewModel.themeSelectionState ?: 0, index = 0) {
            settingsViewModel.changeTheme(0)
            mainViewModel.changeTheme(0)
        }

        SettingsRadioButtonWithTitle(title = "Тёмная тема", state = settingsViewModel.themeSelectionState ?: 0, index = 1) {
            settingsViewModel.changeTheme(1)
            mainViewModel.changeTheme(1)
        }

        SettingsRadioButtonWithTitle(title = "Светлая тема", state = settingsViewModel.themeSelectionState?: 0, index = 2) {
            settingsViewModel.changeTheme(2)
            mainViewModel.changeTheme(2)
        }

    }
}

@Composable
private fun MonetSettings(
    settingsViewModel: SettingsScreenVM,
    mainViewModel: MainViewModel
) {
    SettingsCardWithSwitch(title = "MaterialYou акцент", description = "Устанавливает источник цветовой схемы приложения", state = settingsViewModel.monetTheme ?: true) {
        settingsViewModel.changeMonetUsage(isMonetTheme = it)
        mainViewModel.changeMonetUsage(isMonetColors = it)
    }
}

@Composable
private fun AccentSelector(
    settingsViewModel: SettingsScreenVM,
    mainViewModel: MainViewModel
) {
    
    SettingsExpandableCard(title = "Цвет акцента", subtitle = "Генерирует тему приложения на основе выбранного цвета") {
        LazyRow(modifier = Modifier.padding(12.dp)) {
            itemsIndexed(AccentColorList.list)
            { index, item ->
                ColorPickerItem(settingsViewModel, mainViewModel, item = item, index = index)
            }
        }
    }
}

@Composable
private fun FirstScreenSetting(
    settingsViewModel: SettingsScreenVM
) {
    SettingsCardWithSwitch(
        title = "Начинать с избранного",
        description = "Сделать избранное расписание первым экраном приложения",
        state = settingsViewModel.openFavoriteOnStart ?: false) {
        settingsViewModel.changeFirstScreen(openFavoriteOnStart = it)
    }
}

@Composable
private fun TimeLabelSetting(
    settingsViewModel: SettingsScreenVM,
    timetableViewModel: TimetableScreenVM
) {
    SettingsCardWithSwitch(
        title = "Временные метки",
        description = "Отображение у каждого предмета в расписании времени его начала",
        state = settingsViewModel.timeLabelVisibility ?: false) {
        settingsViewModel.changeTimeLabelVisibility(isTimeLabelVisible = it)
        timetableViewModel.changeTimeLabelVisibility(it)
    }
}

@Composable
private fun SettingsExpandableCard(title: String, subtitle: String = "", content: @Composable () -> Unit) {

    var isMenuExpanded by remember { mutableStateOf(false) }

    val onExpandChange = {
        isMenuExpanded = !isMenuExpanded
    }

    val transition = updateTransition(targetState = isMenuExpanded, label = null)

    val rotateValue by transition.animateFloat(
        transitionSpec = {
            tween(
                durationMillis = 300
            )
        },
        label = ""
    ) { expanded ->
        if (expanded) 180f else 0f
    }

    Card(
        modifier = ModifierWithExpandAnimation
            .padding(
                horizontal = DefaultPadding.CardHorizontalPadding,
                vertical = DefaultPadding.CardVerticalPadding
            )
            .fillMaxWidth()
            .toggleable(value = isMenuExpanded) { onExpandChange() },
        shape = CardShape.CardStandalone,
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.6F)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium
                )
                if (subtitle != "") Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall
                )
            }
                Icon(
                    modifier = Modifier
                        .scale(1.5F)
                        .background(
                            color = MaterialTheme.colorScheme.background,
                            shape = CircleShape
                        )
                        .rotate(rotateValue),
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_down_24dp),
                    contentDescription = "Arrow"
                )

        }
        if (isMenuExpanded) {
            content()
        }
    }
}

@Composable
private fun SettingsCardWithSwitch(title: String, description: String, state: Boolean, action: (Boolean) -> Unit) {
    Card(
        modifier = Modifier
            .padding(
                horizontal = DefaultPadding.CardHorizontalPadding,
                vertical = DefaultPadding.CardVerticalPadding
            )
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 5.dp, horizontal = 12.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.6F)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Switch(
                checked = state,
                onCheckedChange = action
            )
        }
    }
}

@Composable
private fun SettingsRadioButtonWithTitle(title: String, state: Int, index: Int, action: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(vertical = 2.dp, horizontal = 12.dp)
            .fillMaxWidth()
            .clickable(onClick = action),
        verticalAlignment = Alignment.CenterVertically
    )
    {
        RadioButton(
            selected = state == index,
            onClick = action
        )
        Text(modifier = Modifier.padding(start = 8.dp), text = title)
    }
}

@Composable
private fun ColorPickerItem(
    settingsViewModel: SettingsScreenVM,
    mainViewModel: MainViewModel,
    item: AccentColorItem,
    index: Int
) {
    Box(
        modifier = Modifier
            .size(50.dp)
            .padding(4.dp)
            .clip(CircleShape)
            .background(color = item.accentDark)
            .clickable {
                settingsViewModel.changeAccentColor(index)
                mainViewModel.changeAccentColor(index)
            }
    )
}