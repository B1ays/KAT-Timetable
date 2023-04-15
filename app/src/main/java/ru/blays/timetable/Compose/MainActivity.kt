package ru.blays.timetable.Compose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import ru.blays.timetable.Compose.Screens.RootElements
import ru.blays.timetable.Compose.Theme.AviakatTimetableTheme
import ru.blays.timetable.data.models.ObjectBox.Boxes.DaysInTimeTableBox
import ru.blays.timetable.data.models.ObjectBox.Boxes.GroupListBox
import ru.blays.timetable.data.models.ObjectBox.Boxes.MyObjectBox
import ru.blays.timetable.data.models.ObjectBox.Boxes.SubjectsListBox
import ru.blays.timetable.data.repository.preferenceRepository.SettingsRepositoryImpl
import ru.blays.timetable.data.repository.timetableRepository.TimetableRepositoryImplementation
import ru.blays.timetable.data.repository.webRepository.WebRepositoryImpl
import ru.blays.timetable.domain.repository.SettingsRepositoryInterface
import ru.blays.timetable.domain.repository.TimetableRepositoryInterface
import ru.blays.timetable.domain.repository.WebRepositoryInterface
import ru.blays.timetable.domain.useCases.GetGroupsListUseCase
import ru.blays.timetable.domain.useCases.GetTimetableUseCase
import ru.blays.timetable.domain.useCases.ParseGroupsListUseCase
import ru.blays.timetable.domain.useCases.UpdateTimetableUseCase


// Interface implementation //
lateinit var timetableRepositoryImpl: TimetableRepositoryInterface
lateinit var settingsRepositoryImpl: SettingsRepositoryInterface
lateinit var webRepositoryInterface: WebRepositoryInterface

// UseCase implementation //
lateinit var getGroupsListUseCase: GetGroupsListUseCase
lateinit var getTimetableUseCase: GetTimetableUseCase
lateinit var parseGroupsListUseCase: ParseGroupsListUseCase
lateinit var updateTimetableUseCase: UpdateTimetableUseCase

@ExperimentalAnimationApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {

            val objectBoxManager = MyObjectBox.builder()
            .androidContext(applicationContext.applicationContext)
            .build()

            // create objectBox boxes //
            val groupListBox = objectBoxManager.boxFor(GroupListBox::class.java)
            val daysListBox = objectBoxManager.boxFor(DaysInTimeTableBox::class.java)
            val subjectsListBox = objectBoxManager.boxFor(SubjectsListBox::class.java)


            // Interface implementation //
            timetableRepositoryImpl = TimetableRepositoryImplementation(
                groupListBox,
                daysListBox,
                subjectsListBox
            )

            settingsRepositoryImpl = SettingsRepositoryImpl(
                context = applicationContext
            )
            Log.d("InitLog", "Init settingsRepositoryImpl")

            webRepositoryInterface = WebRepositoryImpl()

            Log.d("InitLog", "Init webRepositoryImpl")

            // UseCase implementation //

            Log.d("InitLog", "Init parseGroupsListUseCase")
            parseGroupsListUseCase = ParseGroupsListUseCase(
                timetableRepositoryInterface = timetableRepositoryImpl
            )

            Log.d("InitLog", "Init getGroupsListUseCase")
            getGroupsListUseCase = GetGroupsListUseCase(
                timetableRepositoryInterface = timetableRepositoryImpl,
                webRepositoryInterface = webRepositoryInterface,
                parseGroupsListUseCase = parseGroupsListUseCase
            )

            Log.d("InitLog", "Init getTimetableUseCase")
            getTimetableUseCase = GetTimetableUseCase(
                timetableRepositoryInterface = timetableRepositoryImpl)

            Log.d("InitLog", "Init updateTimetableUseCase")
            updateTimetableUseCase = UpdateTimetableUseCase(
                timetableRepositoryInterface = timetableRepositoryImpl
            )
        }


        if (actionBar != null) {
            actionBar!!.hide()
        }

        setContent {
//            InitSettings()
            AviakatTimetableTheme(/*darkTheme = ThemeState.isDarkMode, dynamicColor = ThemeState.isDynamicColor*/ darkTheme = false, dynamicColor = true) {

                RootElements()
            }
        }
    }
}