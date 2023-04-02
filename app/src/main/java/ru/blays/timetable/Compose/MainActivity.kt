package ru.blays.timetable.Compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import io.objectbox.Box
import ru.blays.timetable.Compose.ComposeElements.RootElements
import ru.blays.timetable.Compose.States.ThemeState
import ru.blays.timetable.Compose.helperClasses.GroupList
import ru.blays.timetable.Compose.theme.AviakatTimetableTheme
import ru.blays.timetable.ObjectBox.Boxes.DaysInTimeTableBox
import ru.blays.timetable.ObjectBox.Boxes.GroupListBox
import ru.blays.timetable.ObjectBox.Boxes.SubjectsListBox
import ru.blays.timetable.ObjectBox.ObjectBoxManager
import ru.blays.timetable.ParseUtils.HTMLParser
import ru.blays.timetable.WebUtils.HTMLClient

lateinit var htmlClient: HTMLClient
lateinit var htmlParser: HTMLParser

lateinit var objectBoxManager: ObjectBoxManager
lateinit var groupListBox: Box<GroupListBox>
lateinit var daysListBox: Box<DaysInTimeTableBox>
lateinit var subjectsListBox: Box<SubjectsListBox>


@ExperimentalAnimationApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            objectBoxManager = ObjectBoxManager()
            objectBoxManager.init(this)
            groupListBox = objectBoxManager.store.boxFor(GroupListBox::class.java)
            daysListBox = objectBoxManager.store.boxFor(DaysInTimeTableBox::class.java)
            subjectsListBox = objectBoxManager.store.boxFor(SubjectsListBox::class.java)
            htmlClient = HTMLClient()
            htmlParser = HTMLParser()
        }
        setContent {
            AviakatTimetableTheme(darkTheme = ThemeState.isDarkMode, dynamicColor = ThemeState.isDynamicColor) {
                GroupList.checkDBState()
                RootElements()
            }
        }
    }
}