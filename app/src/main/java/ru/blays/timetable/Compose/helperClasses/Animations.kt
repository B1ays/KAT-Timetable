package ru.blays.timetable.Compose.helperClasses

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.ui.Modifier


object Animations {

    // Card Expand Animation
    val ModifierWithExpandAnimation = Modifier
        .animateContentSize(
            animationSpec = tween(
            durationMillis = 300,
            easing = FastOutLinearInEasing
        )
    )
}