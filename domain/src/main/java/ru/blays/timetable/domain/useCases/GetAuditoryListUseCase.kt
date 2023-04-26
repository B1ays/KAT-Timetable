package ru.blays.timetable.domain.useCases

import org.jsoup.nodes.Document
import ru.blays.timetable.domain.models.GetSimpleListModel
import ru.blays.timetable.domain.models.SaveSimpleListModel
import ru.blays.timetable.domain.repository.TimetableRepositoryInterface
import ru.blays.timetable.domain.repository.WebRepositoryInterface

class GetAuditoryListUseCase(
    private val timetableRepositoryInterface: TimetableRepositoryInterface,
    private val webRepositoryInterface: WebRepositoryInterface
) {

    private val auditoryListPageHref = "ca.htm"

    suspend fun execute(): List<GetSimpleListModel> {

        val auditoryList = timetableRepositoryInterface.getAuditoryList()

        return auditoryList.ifEmpty {
            try {
                val htmlBody = webRepositoryInterface.getHTMLBody(auditoryListPageHref)
                println("Get subtitle2 List")
                parseLecturersList(htmlBody!!)
            } catch (_: Exception) {
                emptyList()
            }
        }
    }

    private fun parseLecturersList(doc: Document) : List<GetSimpleListModel> {

        val tr = doc.select("tr")

        val lecturersList = mutableListOf<SaveSimpleListModel>()

        tr.forEach { it1 ->
            if (it1.select(".ur").toString() != "") {
                val gr = it1.select(".z0")
                val href = gr.attr("href")
                lecturersList.add(
                    SaveSimpleListModel(
                        name = gr.text(),
                        href = href
                    )
                )
            }
        }
        timetableRepositoryInterface.saveAuditoryList(auditoryList = lecturersList)

        val getLecturersList = mutableListOf<GetSimpleListModel>()

        lecturersList.forEach { lecturers ->
            getLecturersList.add(
                GetSimpleListModel(
                    name = lecturers.name,
                    href = lecturers.href
                )
            )
        }
        return if (lecturersList.isNotEmpty()) getLecturersList else emptyList()
    }

}