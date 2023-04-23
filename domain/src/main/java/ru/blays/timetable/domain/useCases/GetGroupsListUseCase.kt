package ru.blays.timetable.domain.useCases

import org.jsoup.nodes.Document
import ru.blays.timetable.domain.models.GetGroupListModel
import ru.blays.timetable.domain.models.SaveGroupsListModel
import ru.blays.timetable.domain.repository.TimetableRepositoryInterface
import ru.blays.timetable.domain.repository.WebRepositoryInterface

const val groupsListPadeHref = "cg.htm"

class GetGroupsListUseCase(
    private val timetableRepositoryInterface: TimetableRepositoryInterface,
    private val webRepositoryInterface: WebRepositoryInterface
    ) {

    suspend fun execut() : List<GetGroupListModel> {

        val groupsList = timetableRepositoryInterface.getGroupList()

        return groupsList.ifEmpty {
            try {
                val htmlBody = webRepositoryInterface.getHTMLBody(groupsListPadeHref)
                parseGroupList(htmlBody!!)
            } catch (_: Exception) {
                emptyList()
            }
        }
    }

    private fun parseGroupList(doc: Document) : List<GetGroupListModel> {

        val tr = doc.select("tr")

        val groupsList = mutableListOf<SaveGroupsListModel>()

        tr.forEach { it1 ->
            if (it1.select(".ur").toString() != "") {
                val gr = it1.select(".z0")
                val href = gr.attr("href")
                groupsList.add(
                    SaveGroupsListModel(
                        groupCode = gr.text(),
                        href = href
                    )
                )
            }
        }
        timetableRepositoryInterface.saveGroupList(groupsList = groupsList)

        val getGroupList = mutableListOf<GetGroupListModel>()

        groupsList.forEach {groups ->
            getGroupList.add(
                GetGroupListModel(
                    groupCode = groups.groupCode,
                    href = groups.href
                )
            )
        }
        return if (groupsList.isNotEmpty()) getGroupList else emptyList()
    }
}