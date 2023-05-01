package ru.blays.timetable.domain.useCases

import org.jsoup.nodes.Document
import ru.blays.timetable.domain.models.GetSimpleListModel
import ru.blays.timetable.domain.models.SaveSimpleListModel
import ru.blays.timetable.domain.repository.TimetableRepositoryInterface
import ru.blays.timetable.domain.repository.WebRepositoryInterface

class GetLecturerListUseCase(
    private val timetableRepositoryInterface: TimetableRepositoryInterface,
    private val webRepositoryInterface: WebRepositoryInterface
) {

    private val lecturersListPageHref = "cp.htm"

    suspend fun execute(): List<GetSimpleListModel> {

        val lecturersList = timetableRepositoryInterface.getLecturersList()

        return lecturersList.ifEmpty {
            try {
                val htmlBody = webRepositoryInterface.getHTMLBody(lecturersListPageHref)
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

        timetableRepositoryInterface.saveLecturersList(lecturersList = lecturersList)

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