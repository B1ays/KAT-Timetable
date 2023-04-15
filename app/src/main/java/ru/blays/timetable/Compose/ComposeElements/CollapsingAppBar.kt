package ru.blays.timetable.Compose.ComposeElements

/*
@Composable
fun CollapsingAppBar(scrollBehavior: CustomToolbarScrollBehavior) {
    CustomToolbar(
        collapsingTitle = CollapsingTitle.large(AppBarState.titleText),
        centralContent =  {
            if (AppBarState.subtitleVisible) Text(text = CurrentTimeTable.updateTime, color = MaterialTheme.colorScheme.primary)
        },
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
                    onCheckedChange = {
                        TODO(reason = "Непонятно как реализваоть имплементацияю репозитория")
                        */
/*prefs.favoriteTimetablePrefs = ScreenState.currentScreen.Key*//*

                        AppBarState.currentFavoriteTimetable = ScreenState.currentScreen.Key
                    }
                ) {
                    val tint by animateColorAsState(
                        if (AppBarState.favoriteButtonChecked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                    )
                    Icon(
                        modifier = Modifier
                            .scale(1.2F),
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
}*/
