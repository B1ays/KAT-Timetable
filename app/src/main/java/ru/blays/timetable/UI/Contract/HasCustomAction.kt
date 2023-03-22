package ru.blays.timetable.UI.Contract

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

interface HasCustomAction {

    fun getCustomAction(): CustomAction
}
class CustomAction(
    @DrawableRes val iconRes: Int,
    @StringRes val stringRes: Int,
    val onCustomAction: Runnable
)