package ru.blays.timetable.Compose.Screens.GroupListScreen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.objectbox.BoxStore
import ru.blays.timetable.data.models.ObjectBox.Boxes.DaysInTimeTableBox
import ru.blays.timetable.data.models.ObjectBox.Boxes.GroupListBox
import ru.blays.timetable.data.models.ObjectBox.Boxes.SubjectsListBox
import ru.blays.timetable.data.repository.timetableRepository.TimetableRepositoryImplementation
import ru.blays.timetable.data.repository.webRepository.WebRepositoryImpl
import ru.blays.timetable.domain.useCases.GetGroupsListUseCase
import ru.blays.timetable.domain.useCases.ParseGroupsListUseCase

class GroupListVMFactory(context: Context, objectBoxManager: BoxStore) : ViewModelProvider.Factory {

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


    // UseCase implementation //

    private val parseGroupsListUseCase = ParseGroupsListUseCase(
        timetableRepositoryInterface = timetableRepositoryImpl
    )

    private val getGroupsListUseCase = GetGroupsListUseCase(
        timetableRepositoryInterface = timetableRepositoryImpl,
        webRepositoryInterface = webRepositoryInterface,
        parseGroupsListUseCase = parseGroupsListUseCase
    )

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GroupListScreenVM(getGroupsListUseCase) as T
    }

}