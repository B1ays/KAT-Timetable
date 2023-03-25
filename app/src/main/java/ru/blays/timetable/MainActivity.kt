@file:OptIn(ExperimentalMaterial3Api::class)

package ru.blays.timetable

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import io.objectbox.Box
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import ru.blays.timetable.Compose.RootElements
import ru.blays.timetable.ObjectBox.Boxes.DaysInTimeTableBox
import ru.blays.timetable.ObjectBox.Boxes.GroupListBox
import ru.blays.timetable.ObjectBox.Boxes.SubjectsListBox
import ru.blays.timetable.ObjectBox.ObjectBoxManager
import ru.blays.timetable.ParseUtils.HTMLParser
import ru.blays.timetable.WebUtils.HTMLClient
import ru.blays.timetable.ui.theme.AviakatTimetableTheme

lateinit var htmlClient: HTMLClient
lateinit var htmlParser: HTMLParser

lateinit var objectBoxManager: ObjectBoxManager
lateinit var groupListBox: Box<GroupListBox>
lateinit var daysListBox: Box<DaysInTimeTableBox>
lateinit var subjectsListBox: Box<SubjectsListBox>


class MainActivity : ComponentActivity() {
    
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AviakatTimetableTheme {
                RootElements()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        objectBoxManager = ObjectBoxManager()
        objectBoxManager.init(this)
        groupListBox = objectBoxManager.store.boxFor(GroupListBox::class.java)
        daysListBox = objectBoxManager.store.boxFor(DaysInTimeTableBox::class.java)
        subjectsListBox = objectBoxManager.store.boxFor(SubjectsListBox::class.java)
        htmlClient = HTMLClient()
        htmlParser = HTMLParser()
        MainScope().launch { checkDBState() }
    }

    private suspend fun checkDBState(): Boolean {
        return if (groupListBox.isEmpty) {
            val job = MainScope().launch { htmlParser.createMainDB() }
            job.isCompleted
        } else {
            true
        }
    }
}




