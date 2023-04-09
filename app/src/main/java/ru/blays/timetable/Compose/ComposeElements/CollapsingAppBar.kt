package ru.blays.timetable.Compose.ComposeElements

import androidx.compose.animation.animateColorAsState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import ru.blays.timetable.Compose.States.AppBarState
import ru.blays.timetable.Compose.States.ScreenState
import ru.blays.timetable.Compose.prefs
import ru.blays.timetable.R
import ru.hh.toolbar.custom_toolbar.CollapsingTitle
import ru.hh.toolbar.custom_toolbar.CustomToolbar
import ru.hh.toolbar.custom_toolbar.CustomToolbarScrollBehavior

@Composable
fun CollapsingAppBar(scrollBehavior: CustomToolbarScrollBehavior) {
    CustomToolbar(
        collapsingTitle = CollapsingTitle.large(AppBarState.titleText),
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (AppBarState.navigateBackButtonVisible) {
                IconButton(
                    onClick = { onBack() }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Navigation back button"
                    )
                }
            }
        },
        actions = {
            if (AppBarState.favoriteButtonVisible) {
                IconToggleButton(
                    checked = AppBarState.favoriteButtonChecked,
                    onCheckedChange = { prefs.favoriteTimetablePrefs = ScreenState.currentScreen.Key
                        AppBarState.currentFavoriteTimetable = ScreenState.currentScreen.Key
                    }
                ) {
                    val tint by animateColorAsState(
                        if (AppBarState.favoriteButtonChecked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                    )
                    Icon(
                        imageVector = if (AppBarState.favoriteButtonChecked) {
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