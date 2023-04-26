package ru.blays.timetable.domain.useCases

import org.jsoup.nodes.Document
import ru.blays.timetable.domain.models.GetSimpleListModel
import ru.blays.timetable.domain.models.SaveSimpleListModel
import ru.blays.timetable.domain.repository.TimetableRepositoryInterface
import ru.blays.timetable.domain.repository.WebRepositoryInterface

class GetGroupsListUseCase(
    private val timetableRepositoryInterface: TimetableRepositoryInterface,
    private val webRepositoryInterface: WebRepositoryInterface
    ) {

    private val groupsListPageHref = "cg.htm"

    suspend fun execut() : List<GetSimpleListModel> {

        val groupsList = timetableRepositoryInterface.getGroupList()

        return groupsList.ifEmpty {
            try {
                val htmlBody = webRepositoryInterface.getHTMLBody(groupsListPageHref)
                println("Get groups List")
                parseGroupList(htmlBody!!)
            } catch (_: Exception) {
                emptyList()
            }
        }
    }

    private fun parseGroupList(doc: Document) : List<GetSimpleListModel> {

        val tr = doc.select("tr")

        val groupsList = mutableListOf<SaveSimpleListModel>()

        tr.forEach { it1 ->
            if (it1.select(".ur").toString() != "") {
                val gr = it1.select(".z0")
                val href = gr.attr("href")
                groupsList.add(
                    SaveSimpleListModel(
                        name = gr.text(),
                        href = href
                    )
                )
            }
        }
        timetableRepositoryInterface.saveGroupList(groupsList = groupsList)

        val getGroupList = mutableListOf<GetSimpleListModel>()

        groupsList.forEach {groups ->
            getGroupList.add(
                GetSimpleListModel(
                    name = groups.name,
                    href = groups.href
                )
            )
        }
        return if (groupsList.isNotEmpty()) getGroupList else emptyList()
    }
}