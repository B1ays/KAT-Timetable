package ru.blays.timetable.Compose.ComposeElements

/*@Composable
fun SettingsScreen() {
    Column {
        ThemeSettings()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) MonetSettings()
        AccentSelector()
    }

}

@Composable
fun ThemeSettings() {
    var radioButtonSelectionState by remember { mutableStateOf(prefs.themePrefs) }
    val isDarkMode = isSystemInDarkTheme()
    var isMenuExpanded by remember { mutableStateOf(false) }

    val onExpandChange = {
        isMenuExpanded = !isMenuExpanded
    }

    val setSystemTheme = {
        radioButtonSelectionState = 0
        prefs.themePrefs = 0
        ThemeState.isDarkMode = isDarkMode
    }
    val setDarkTheme = {
        radioButtonSelectionState = 1
        prefs.themePrefs = 1
        ThemeState.isDarkMode = true
    }

    val setLightTheme = {
        radioButtonSelectionState = 2
        prefs.themePrefs = 2
        ThemeState.isDarkMode = false
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
            .padding(horizontal = DefaultPadding.CardHorizontalPadding, vertical = DefaultPadding.CardVerticalPadding)
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
            Text(text = "Тема приложения")
            Icon(
                modifier = Modifier
                    .scale(1.5F)
                    .background(color = MaterialTheme.colorScheme.background, shape = CircleShape)
                    .rotate(rotateValue),
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_down_24dp),
                contentDescription = "Arrow")
        }
        if (isMenuExpanded) {
            Row(
                modifier = Modifier
                    .padding(vertical = 2.dp, horizontal = 12.dp)
                    .fillMaxWidth()
                    .clickable {
                        setSystemTheme()
                    },
                verticalAlignment = Alignment.CenterVertically
            )
            {
                RadioButton(
                    selected = radioButtonSelectionState == 0,
                    onClick = { setSystemTheme() }
                )
                Text(modifier = Modifier.padding(start = 8.dp), text = "Системная тема")
            }

            Row(
                modifier = Modifier
                    .padding(vertical = 2.dp, horizontal = 12.dp)
                    .fillMaxWidth()
                    .clickable {
                        setDarkTheme()
                    },
                verticalAlignment = Alignment.CenterVertically
            )
            {
                RadioButton(
                    selected = radioButtonSelectionState == 1,
                    onClick = { setDarkTheme() }
                )
                Text(modifier = Modifier.padding(start = 8.dp), text = "Тёмная тема")
            }

            Row(
                modifier = Modifier
                    .padding(vertical = 2.dp, horizontal = 12.dp)
                    .fillMaxWidth()
                    .clickable {
                        setLightTheme()
                    },
                verticalAlignment = Alignment.CenterVertically
            )
            {
                RadioButton(
                    selected = radioButtonSelectionState == 2,
                    onClick = { setLightTheme() }
                )
                Text(modifier = Modifier.padding(start = 8.dp), text = "Светлая тема")
            }
        }
    }
}

@Composable
fun MonetSettings() {
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
            Text(
                text = "MaterialYou акцент",

            )
            Switch(
                checked = ThemeState.isDynamicColor,
                onCheckedChange = {
                    prefs.monetPrefs = it
                    ThemeState.changeDynamicColor()
                })
        }
    }
}

@Composable
fun AccentSelector() {
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
        Text(text = "Цвет акцента")
        Icon(
            modifier = Modifier
                .scale(1.5F)
                .background(color = MaterialTheme.colorScheme.background, shape = CircleShape)
                .rotate(rotateValue),
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_down_24dp),
            contentDescription = "Arrow")
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
fun ColorPickerItem(item: AccentColorItem, index: Int) {
Box(
    modifier = Modifier
        .size(50.dp)
        .padding(4.dp)
        .clip(CircleShape)
        .background(color = item.accentDark)
        .clickable {
            prefs.accentColorPrefs = index
            ThemeState.changeAccentColor(item)
        }
    )
}*/

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
