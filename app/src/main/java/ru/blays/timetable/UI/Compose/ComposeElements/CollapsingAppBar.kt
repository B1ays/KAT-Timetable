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
import ru.blays.timetable.R
import ru.blays.timetable.UI.Compose.ComposeElements.navigation.NavigationVM
import ru.blays.timetable.UI.Compose.MainViewModel
import ru.blays.timetable.UI.ComposeElements.onBack
import ru.hh.toolbar.custom_toolbar.CollapsingTitle
import ru.hh.toolbar.custom_toolbar.CustomToolbar
import ru.hh.toolbar.custom_toolbar.CustomToolbarScrollBehavior

class CollapsingAppBar(private val mainViewModel: MainViewModel, private val navigationViewModel: NavigationVM) {
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
                        onClick = { onBack(navigationViewModel) }
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
                            mainViewModel.setAsFavorite(navigationViewModel.currentScreen.Key.href)
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
}