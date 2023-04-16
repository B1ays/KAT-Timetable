package ru.blays.timetable.Compose.ComposeElements

import androidx.compose.animation.animateColorAsState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import ru.blays.timetable.Compose.HelperClasses.CurrentTimeTable
import ru.blays.timetable.Compose.mainViewModel
import ru.blays.timetable.R
import ru.hh.toolbar.custom_toolbar.CollapsingTitle
import ru.hh.toolbar.custom_toolbar.CustomToolbar
import ru.hh.toolbar.custom_toolbar.CustomToolbarScrollBehavior

@Composable
fun CollapsingAppBar(scrollBehavior: CustomToolbarScrollBehavior) {
    CustomToolbar(
        collapsingTitle = CollapsingTitle.large(mainViewModel.titleText),
        centralContent =  {
            if (mainViewModel.subtitleVisible) Text(text = CurrentTimeTable.updateTime, color = MaterialTheme.colorScheme.primary)
        },
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (mainViewModel.navigateBackButtonVisible) {
                IconButton(
                    onClick = { /*onBack()*/ }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Navigation back button"
                    )
                }
            }
        },
        actions = {
            if (mainViewModel.favoriteButtonVisible) {
                IconToggleButton(
                    checked = mainViewModel.favoriteButtonChecked,
                    onCheckedChange = {
                        TODO(reason = "Непонятно как реализваоть имплементацияю репозитория")
                        /*prefs.favoriteTimetablePrefs = ScreenState.currentScreen.Key
                        AppBarState.currentFavoriteTimetable = ScreenState.currentScreen.Key*/
                    }
                ) {
                    val tint by animateColorAsState(
                        if (mainViewModel.favoriteButtonChecked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                    )
                    Icon(
                        modifier = Modifier
                            .scale(1.2F),
                        imageVector = if (mainViewModel.favoriteButtonChecked) {
                            ImageVector.vectorResource(
                                id = R.drawable.round_star_24
                            )
                        } else ImageVector.vectorResource(
                            id = R.drawable.round_star_border_24
                        ),
                        contentDescription = "Favorite Button",
                        tint = tint
                    )
                }
            }
        }
    )
}
