package ru.blays.timetable.Compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import ru.blays.timetable.Compose.HelperClasses.GroupList
import ru.blays.timetable.Compose.HelperClasses.InitSettings
import ru.blays.timetable.Compose.Screens.RootElements
import ru.blays.timetable.Compose.States.ThemeState
import ru.blays.timetable.Compose.Theme.AviakatTimetableTheme
import ru.blays.timetable.ParseUtils.HTMLParser
import ru.blays.timetable.WebUtils.HTMLClient

lateinit var htmlClient: HTMLClient
lateinit var htmlParser: HTMLParser
/*lateinit var prefs: Prefs*/

/*lateinit var objectBoxManager: ObjectBoxManager
lateinit var groupListBox: Box<ru.blays.timetable.data.models.ObjectBox.Boxes.GroupListBox>
lateinit var daysListBox: Box<ru.blays.timetable.data.models.ObjectBox.Boxes.DaysInTimeTableBox>
lateinit var subjectsListBox: Box<ru.blays.timetable.data.models.ObjectBox.Boxes.SubjectsListBox>*/


@ExperimentalAnimationApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            /*objectBoxManager = ObjectBoxManager()
            objectBoxManager.init(this)
            groupListBox = objectBoxManager.store.boxFor(ru.blays.timetable.data.models.ObjectBox.Boxes.GroupListBox::class.java)
            daysListBox = objectBoxManager.store.boxFor(ru.blays.timetable.data.models.ObjectBox.Boxes.DaysInTimeTableBox::class.java)
            subjectsListBox = objectBoxManager.store.boxFor(ru.blays.timetable.data.models.ObjectBox.Boxes.SubjectsListBox::class.java)*/
            htmlClient = HTMLClient()
            htmlParser = HTMLParser()
            /*prefs = Prefs(this)*/
        }

        if (actionBar != null) {
            actionBar!!.hide()
        }

        setContent {
            InitSettings()
            AviakatTimetableTheme(darkTheme = ThemeState.isDarkMode, dynamicColor = ThemeState.isDynamicColor) {
                GroupList.checkDBState()
                /*if (prefs.firstStart) {
                    LandingScreen()
                } else*/
                RootElements()
            }
        }
    }
}