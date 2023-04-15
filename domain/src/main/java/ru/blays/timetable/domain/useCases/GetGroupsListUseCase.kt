package ru.blays.timetable.domain.useCases

import ru.blays.timetable.domain.models.GetGroupListModel
import ru.blays.timetable.domain.repository.TimetableRepositoryInterface
import ru.blays.timetable.domain.repository.WebRepositoryInterface

const val groupsListPadeHref = "cg.htm"

class GetGroupsListUseCase(
    private val timetableRepositoryInterface: TimetableRepositoryInterface,
    private val webRepositoryInterface: WebRepositoryInterface,
    private val parseGroupsListUseCase: ParseGroupsListUseCase
    ) {

    suspend fun execut() : List<GetGroupListModel> {

        val groupsList = timetableRepositoryInterface.getGroupList()

        if (groupsList.isNotEmpty()) {
             return groupsList
        } else {
            val htmlBody = webRepositoryInterface.getHTMLBody(groupsListPadeHref)
            return parseGroupsListUseCase.execut(htmlBody!!)
        }
    }

}