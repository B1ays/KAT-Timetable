package ru.blays.timetable.Compose

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.objectbox.BoxStore
import ru.blays.timetable.data.models.ObjectBox.Boxes.DaysInTimeTableBox
import ru.blays.timetable.data.models.ObjectBox.Boxes.GroupListBox
import ru.blays.timetable.data.models.ObjectBox.Boxes.SubjectsListBox
import ru.blays.timetable.data.repository.preferenceRepository.SettingsRepositoryImpl
import ru.blays.timetable.data.repository.timetableRepository.TimetableRepositoryImplementation
import ru.blays.timetable.data.repository.webRepository.WebRepositoryImpl
import ru.blays.timetable.domain.useCases.GetGroupsListUseCase
import ru.blays.timetable.domain.useCases.GetTimetableUseCase
import ru.blays.timetable.domain.useCases.ParseGroupsListUseCase
import ru.blays.timetable.domain.useCases.ParseTimetableUseCase
import ru.blays.timetable.domain.useCases.UpdateTimetableUseCase

class MainViewModelFactory(context: Context, objectBoxManager: BoxStore) : ViewModelProvider.Factory {

    // create objectBox boxes //
    private val groupListBox = objectBoxManager.boxFor(GroupListBox::class.java)
    private val daysListBox = objectBoxManager.boxFor(DaysInTimeTableBox::class.java)
    private val subjectsListBox = objectBoxManager.boxFor(SubjectsListBox::class.java)


    // Interface implementation //
    private val timetableRepositoryImpl = TimetableRepositoryImplementation(
    groupListBox,
    daysListBox,
    subjectsListBox
    )

    private val settingsRepositoryImpl = SettingsRepositoryImpl(
    context = context
    )


    private val webRepositoryInterface = WebRepositoryImpl()


    // UseCase implementation //

    private val parseGroupsListUseCase = ParseGroupsListUseCase(
    timetableRepositoryInterface = timetableRepositoryImpl
    )

    private val parseTimetableUseCase = ParseTimetableUseCase(
        timetableRepositoryInterface = timetableRepositoryImpl
    )

    private val getGroupsListUseCase = GetGroupsListUseCase(
    timetableRepositoryInterface = timetableRepositoryImpl,
    webRepositoryInterface = webRepositoryInterface,
    parseGroupsListUseCase = parseGroupsListUseCase
    )

    private val getTimetableUseCase = GetTimetableUseCase(
    timetableRepositoryInterface = timetableRepositoryImpl,
    webRepositoryInterface = webRepositoryInterface,
    parseTimetableUseCase = parseTimetableUseCase)

    private val updateTimetableUseCase = UpdateTimetableUseCase(
    timetableRepositoryInterface = timetableRepositoryImpl
    )

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(getGroupsListUseCase, getTimetableUseCase, updateTimetableUseCase) as T
    }
}