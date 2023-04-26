package ru.blays.timetable.UI.Compose.Screens.GroupListScreen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.objectbox.BoxStore
import ru.blays.timetable.data.models.ObjectBox.Boxes.AuditoryListBox
import ru.blays.timetable.data.models.ObjectBox.Boxes.DaysInTimeTableBox
import ru.blays.timetable.data.models.ObjectBox.Boxes.GroupListBox
import ru.blays.timetable.data.models.ObjectBox.Boxes.LecturersListBox
import ru.blays.timetable.data.models.ObjectBox.Boxes.SubjectsListBox
import ru.blays.timetable.data.repository.timetableRepository.TimetableRepositoryImplementation
import ru.blays.timetable.data.repository.webRepository.WebRepositoryImpl
import ru.blays.timetable.domain.useCases.GetAuditoryListUseCase
import ru.blays.timetable.domain.useCases.GetGroupsListUseCase
import ru.blays.timetable.domain.useCases.GetLecturerListUseCase

class GroupListVMFactory(context: Context, objectBoxManager: BoxStore) : ViewModelProvider.Factory {

    // create objectBox boxes //
    private val groupListBox = objectBoxManager.boxFor(GroupListBox::class.java)
    private val daysListBox = objectBoxManager.boxFor(DaysInTimeTableBox::class.java)
    private val subjectsListBox = objectBoxManager.boxFor(SubjectsListBox::class.java)
    private val lecturersListBox = objectBoxManager.boxFor(LecturersListBox::class.java)
    private val auditoryListBox = objectBoxManager.boxFor(AuditoryListBox::class.java)


    // Interface implementation //
    private val timetableRepositoryImpl = TimetableRepositoryImplementation(
        groupListBox,
        lecturersListBox,
        auditoryListBox,
        daysListBox,
        subjectsListBox
    )

    private val webRepositoryInterface = WebRepositoryImpl()

    private val getGroupsListUseCase = GetGroupsListUseCase(
        timetableRepositoryInterface = timetableRepositoryImpl,
        webRepositoryInterface = webRepositoryInterface
    )

    private val getLecturerListUseCase = GetLecturerListUseCase(
        timetableRepositoryInterface = timetableRepositoryImpl,
        webRepositoryInterface = webRepositoryInterface
    )

    private val getAuditoryListUseCase = GetAuditoryListUseCase(
        timetableRepositoryInterface = timetableRepositoryImpl,
        webRepositoryInterface = webRepositoryInterface
    )

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SimpleListScreenVM(getGroupsListUseCase, getLecturerListUseCase, getAuditoryListUseCase) as T
    }

}