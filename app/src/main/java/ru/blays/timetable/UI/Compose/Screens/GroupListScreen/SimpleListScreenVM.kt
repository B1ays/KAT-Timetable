package ru.blays.timetable.UI.Compose.Screens.GroupListScreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.blays.timetable.domain.models.GetSimpleListModel
import ru.blays.timetable.domain.useCases.GetAuditoryListUseCase
import ru.blays.timetable.domain.useCases.GetGroupsListUseCase
import ru.blays.timetable.domain.useCases.GetLecturerListUseCase

@OptIn(ExperimentalFoundationApi::class)
class SimpleListScreenVM(
    private val getGroupsListUseCase: GetGroupsListUseCase,
    private val getLecturerListUseCase: GetLecturerListUseCase,
    private val getAuditoryListUseCase: GetAuditoryListUseCase
) : ViewModel() {

    var isRefreshing by mutableStateOf(true)

    var listGroups by mutableStateOf(listOf<GetSimpleListModel>())

    var listLecturer by mutableStateOf(listOf<GetSimpleListModel>())

    var listAuditory by mutableStateOf(listOf<GetSimpleListModel>())

    val pagerState: PagerState = PagerState(initialPage = 1)

    fun get() {
        CoroutineScope(Dispatchers.IO).launch {

            if (listGroups.isEmpty()) {
                isRefreshing = true
                listGroups = getGroupsListUseCase.execut()
                isRefreshing = false
            }

            if(listLecturer.isEmpty()) {
                isRefreshing = true
                listLecturer = getLecturerListUseCase.execute()
                isRefreshing = false
            }

            if(listAuditory.isEmpty()) {
                isRefreshing = true
                listAuditory = getAuditoryListUseCase.execute()
                isRefreshing = false
            }
        }
    }
}
