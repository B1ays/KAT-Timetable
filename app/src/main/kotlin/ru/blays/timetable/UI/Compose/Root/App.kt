package ru.blays.timetable.UI.Compose.Root

import android.app.Application
import androidx.compose.animation.ExperimentalAnimationApi
import ru.blays.timetable.UI.Compose.Root.MainActivity
import ru.blays.timetable.data.models.ObjectBox.Boxes.MyObjectBox

class App : Application() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate() {
        super.onCreate()
        MainActivity.ObjectBox.objectBoxManager = MyObjectBox.builder()
            .androidContext(this)
            .build()
    }
}