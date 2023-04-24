package ru.blays.timetable.UI.ComposeElements

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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
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
import ru.blays.timetable.R
import ru.blays.timetable.UI.Compose.Screens.SettingsScreen.SettingsScreenVM
import ru.blays.timetable.UI.DataClasses.AccentColorItem
import ru.blays.timetable.UI.DataClasses.AccentColorList
import ru.blays.timetable.UI.DataClasses.Animations.ModifierWithExpandAnimation
import ru.blays.timetable.UI.DataClasses.CardShape
import ru.blays.timetable.UI.DataClasses.DefaultPadding

class SettingsScreen(private val settingsViewModel: SettingsScreenVM) {

    @Composable
    fun Create() {

        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
                .verticalScroll(state = scrollState)
        )
        {
            ThemeSettings()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) MonetSettings()
            AccentSelector()
            FirstScreenSetting()
        }
    }

    @Composable
    private fun ThemeSettings() {

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
        )
        { expanded ->
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
                Text(
                    text = "Тема приложения", 
                    style = MaterialTheme.typography.titleMedium
                )
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
                Row(
                    modifier = Modifier
                        .padding(vertical = 2.dp, horizontal = 12.dp)
                        .fillMaxWidth()
                        .clickable {
                            settingsViewModel.changeTheme(0)
                        },
                    verticalAlignment = Alignment.CenterVertically
                )
                {
                    RadioButton(
                        selected = settingsViewModel.themeSelectionState == 0,
                        onClick = { settingsViewModel.changeTheme(0) }
                    )
                    Text(modifier = Modifier.padding(start = 8.dp), text = "Системная тема")
                }

                Row(
                    modifier = Modifier
                        .padding(vertical = 2.dp, horizontal = 12.dp)
                        .fillMaxWidth()
                        .clickable {
                            settingsViewModel.changeTheme(1)
                        },
                    verticalAlignment = Alignment.CenterVertically
                )
                {
                    RadioButton(
                        selected = settingsViewModel.themeSelectionState == 1,
                        onClick = { settingsViewModel.changeTheme(1) }
                    )
                    Text(modifier = Modifier.padding(start = 8.dp), text = "Тёмная тема")
                }

                Row(
                    modifier = Modifier
                        .padding(vertical = 2.dp, horizontal = 12.dp)
                        .fillMaxWidth()
                        .clickable {
                            settingsViewModel.changeTheme(2)
                        },
                    verticalAlignment = Alignment.CenterVertically
                )
                {
                    RadioButton(
                        selected = settingsViewModel.themeSelectionState == 2,
                        onClick = { settingsViewModel.changeTheme(2) }
                    )
                    Text(modifier = Modifier.padding(start = 8.dp), text = "Светлая тема")
                }
            }
        }
    }

    @Composable
    private fun MonetSettings() {
        Card(
            modifier = Modifier
                .padding(
                    horizontal = DefaultPadding.CardHorizontalPadding,
                    vertical = DefaultPadding.CardVerticalPadding
                )
                .fillMaxWidth(),
            shape = CardShape.CardStandalone,
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(vertical = 5.dp, horizontal = 12.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Column(modifier = Modifier
                    .fillMaxWidth(0.6F)
                ) {
                    Text(
                        text = "MaterialYou акцент",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "Устанавливает источник цветовой схемы приложения",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Switch(
                    checked = settingsViewModel.monetTheme ?: true,
                    onCheckedChange = {
                        settingsViewModel.changeMonetUsage(isMonetTheme = it)
                    })
            }
        }
    }

    @Composable
    private fun AccentSelector() {
        var isMenuExpanded by remember {
            mutableStateOf(false)
        }
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
        )
        { expanded ->
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
                Column(modifier = Modifier
                    .fillMaxWidth(0.6F)
                ) {
                    Text(
                        text = "Цвет акцента",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "Генерирует тему приложения на основе выбранного цвета",
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
                LazyRow(modifier = Modifier.padding(12.dp)) {
                    itemsIndexed(AccentColorList.list)
                    { index, item ->
                        ColorPickerItem(item = item, index = index)
                    }
                }
            }
        }
    }

    @Composable
    private fun ColorPickerItem(item: AccentColorItem, index: Int) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .padding(4.dp)
                .clip(CircleShape)
                .background(color = item.accentDark)
                .clickable {
                    settingsViewModel.changeAccentColor(index)
                }
        )
    }


    @Composable
    private fun FirstScreenSetting() {
        Card(
            modifier = Modifier
                .padding(
                    horizontal = DefaultPadding.CardHorizontalPadding,
                    vertical = DefaultPadding.CardVerticalPadding
                )
                .fillMaxWidth(),
            shape = CardShape.CardStandalone,
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(vertical = 5.dp, horizontal = 12.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Column(modifier = Modifier
                    .fillMaxWidth(0.6F)
                ) {
                    Text(
                        text = "Начинать с избранного",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "Сделать избранное расписание первым экраном приложения",
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Switch(
                    checked = settingsViewModel.openFavoriteOnStart ?: false,
                    onCheckedChange = {
                        settingsViewModel.changeFirstScreen(openFavoriteOnStart = it)
                    }
                )
            }
        }
    }
}


/*
@Composable
fun DBStateItem() {
    Card(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(6.dp)
                .fillMaxWidth()
        )
        {
            Text(
                modifier = Modifier
                    .padding(bottom = 6.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium,
                text = "Состояние базы данных",
            )
            Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
                Text(
                    textAlign = TextAlign.Center,
                    text = "База данных в норме")
                Icon(
                    modifier = Modifier
                        .size(60.dp),
                    imageVector = androidx.compose.material.icons.Icons.Rounded.CheckCircle,
                    contentDescription = "state icon",
                    tint = Color.Green
                )
            }
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd) {
                Button(modifier = Modifier
                    .padding(top = 4.dp),
                    onClick = { */
/*TODO*//*
 }
                ) {
                    Text(text = "Пересоздать базу данных")
                }
            }
        }
    }
}*/
