package ru.blays.timetable.Compose.Screens.GroupListScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import ru.blays.timetable.domain.models.GetGroupListModel
import ru.blays.timetable.domain.useCases.GetGroupsListUseCase

class GroupListScreenVM(private val getGroupsListUseCase: GetGroupsListUseCase) : ViewModel() {

    var groupList by mutableStateOf(listOf<GetGroupListModel>())

    suspend fun get() {
        groupList = getGroupsListUseCase.execut()
    }

}