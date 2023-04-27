package ru.blays.timetable.UI.Compose.ComposeElements

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
import androidx.navigation.NavHostController
import ru.blays.timetable.R
import ru.blays.timetable.UI.Compose.Root.MainViewModel
import ru.blays.timetable.UI.Compose.Screens.TimeTableScreen.TimetableScreenVM
import ru.hh.toolbar.custom_toolbar.CollapsingTitle
import ru.hh.toolbar.custom_toolbar.CustomToolbar
import ru.hh.toolbar.custom_toolbar.CustomToolbarScrollBehavior


class CollapsingAppBar(private val mainViewModel: MainViewModel, private val timetableViewModel: TimetableScreenVM, private val navigation: NavHostController) {
    @Composable
    fun Create(scrollBehavior: CustomToolbarScrollBehavior) {
        CustomToolbar(
            collapsingTitle = CollapsingTitle.large(mainViewModel.titleText),
            centralContent =  {
                if (mainViewModel.subtitleVisible) Text(text = mainViewModel.subtitleText, color = MaterialTheme.colorScheme.primary)
            },
            scrollBehavior = scrollBehavior,
            navigationIcon = {
                if (mainViewModel.navigateBackButtonVisible) {
                    IconButton(
                        onClick = { navigation.navigateUp() },
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
                            mainViewModel.setAsFavorite(timetableViewModel.currentHref, timetableViewModel.currentSource)
                        }
                    ) {
                        val tint by animateColorAsState(
                            if (mainViewModel.favoriteHref == timetableViewModel.currentHref) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                        )
                        Icon(
                            modifier = Modifier
                                .scale(1.2F),
                            imageVector = if (mainViewModel.favoriteHref == timetableViewModel.currentHref) {
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
}