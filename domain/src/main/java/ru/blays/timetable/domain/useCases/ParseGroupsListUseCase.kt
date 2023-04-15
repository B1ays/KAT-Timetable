package ru.blays.timetable.domain.useCases

import org.jsoup.nodes.Document
import ru.blays.timetable.domain.models.GetGroupListModel
import ru.blays.timetable.domain.models.SaveGroupsListModel
import ru.blays.timetable.domain.repository.TimetableRepositoryInterface

class ParseGroupsListUseCase(private val timetableRepositoryInterface: TimetableRepositoryInterface) {

    fun execut(doc: Document) : List<GetGroupListModel> {

        /*objectBoxManager.deleteBox(listOf(daysListBox, groupListBox, subjectsListBox))*/

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