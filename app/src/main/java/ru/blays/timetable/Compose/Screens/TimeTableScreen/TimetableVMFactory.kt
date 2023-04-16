package ru.blays.timetable.Compose.Screens.TimeTableScreen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.objectbox.BoxStore
import ru.blays.timetable.data.models.ObjectBox.Boxes.DaysInTimeTableBox
import ru.blays.timetable.data.models.ObjectBox.Boxes.GroupListBox
import ru.blays.timetable.data.models.ObjectBox.Boxes.SubjectsListBox
import ru.blays.timetable.data.repository.timetableRepository.TimetableRepositoryImplementation
import ru.blays.timetable.data.repository.webRepository.WebRepositoryImpl
import ru.blays.timetable.domain.useCases.GetTimetableUseCase
import ru.blays.timetable.domain.useCases.ParseTimetableUseCase
import ru.blays.timetable.domain.useCases.UpdateTimetableUseCase

class TimetableVMFactory(context: Context, objectBoxManager: BoxStore) : ViewModelProvider.Factory {

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

    private val webRepositoryInterface = WebRepositoryImpl()

    private val parseTimetableUseCase = ParseTimetableUseCase(timetableRepositoryInterface = timetableRepositoryImpl)


    // UseCase implementation //

    private val getTimetableUseCase = GetTimetableUseCase(
        timetableRepositoryInterface = timetableRepositoryImpl,
        webRepositoryInterface = webRepositoryInterface,
        parseTimetableUseCase = parseTimetableUseCase)

    private val updateTimetableUseCase = UpdateTimetableUseCase(
        timetableRepositoryInterface = timetableRepositoryImpl
    )


    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TimetableScreenVM(getTimetableUseCase, updateTimetableUseCase) as T
    }

}