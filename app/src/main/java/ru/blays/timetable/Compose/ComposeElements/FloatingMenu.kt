package ru.blays.timetable.Compose.ComposeElements


/*
@ExperimentalAnimationApi
@Composable
fun FloatingMenu() {
    Box(
        modifier = Modifier
            .background(
                shape = RoundedCornerShape(10.dp),
                color = MaterialTheme.colorScheme.primary
            )
            .clickable(enabled = !isExpanded)
            {
                changeExpanded()
            }
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 200,
                    easing = FastOutLinearInEasing
                )
            )
        )
    {
        if (isExpanded) {

            // Развёрнутое меню

            val visibilityState = remember {
                MutableTransitionState(false).apply {
                    targetState = true
                }
            }

            AnimatedVisibility(
                visibleState = visibilityState,
                enter = scaleIn(),
                exit = scaleOut()
            ) {
                Column(
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .padding(horizontal = 10.dp)
                        .fillMaxWidth(0.7f)
                )
                {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 6.dp),
                        text = "Меню",
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.background,
                        style = MaterialTheme.typography.titleLarge
                    )
                    for (item in FloatingMenuItems.Items) {
                        FloatingMenuItem(item)
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {

                        if (currentScreen.Screen == ScreenList.timetable_screen) FloatingMenuAction(item = FloatingMenuActions.refresh)
                        FloatingMenuAction(item = FloatingMenuActions.close)
                    }
                }
            }
        }
        // Свёрнутое меню
        else {
            Icon(
                modifier = Modifier
                    .padding(14.dp),
                imageVector = Icons.Rounded.Menu,
                contentDescription = "Menu button",
                tint = MaterialTheme.colorScheme.background
            )
        }
    }
}

@Composable
fun FloatingMenuItem(
    item: FloatingMenuItemsModel
) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 3.dp)
        .clickable {
            changeExpanded()
            item.action()
        },
        shape = RoundedCornerShape(4.dp)
    )
    {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(7.dp)
        )
        {
            Icon(
                imageVector = item.icon,
                contentDescription = "Menu item",
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                modifier = Modifier
                    .padding(start = 6.dp),
                text = item.title,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun FloatingMenuAction(
    item: FloatingMenuActionsModel
) {
    IconButton(
        onClick = {
            item.action()
        }
    )
    {
        Icon(
            modifier = Modifier
                .size(40.dp)
                .padding(horizontal = 4.dp),
            imageVector = item.icon,
            contentDescription = "Close button",
            tint = MaterialTheme.colorScheme.background
        )
    }
}*/
