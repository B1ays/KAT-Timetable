package ru.blays.timetable.UI.Compose.Screens.GroupListScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.blays.timetable.domain.models.GetGroupListModel
import ru.blays.timetable.domain.useCases.GetGroupsListUseCase

class GroupListScreenVM(private val getGroupsListUseCase: GetGroupsListUseCase) : ViewModel() {

    var groupList by mutableStateOf(listOf<GetGroupListModel>())

    init {
        CoroutineScope(Dispatchers.IO).launch {
            groupList = getGroupsListUseCase.execut()
        }
    }

}