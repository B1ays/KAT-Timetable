package ru.blays.timetable.domain.useCases

import ru.blays.timetable.domain.models.ChartModel
import ru.blays.timetable.domain.repository.WebRepositoryInterface

class GetChartsForGroupUseCase(private val webRepositoryInterface: WebRepositoryInterface) {
    suspend fun execute(href: String): List<ChartModel> {

        try {

            val doc = webRepositoryInterface.getHTMLBody(href)

            val chartsList = mutableListOf<ChartModel>()

            if (doc != null) {
                val table = doc.select("table.inf")

                val tr = table.select("tr")

                for (i in tr) {
                    val chart = ChartModel()
                    val vp = i.select(".vp")
                    /*println(vp)*/
                    if (vp.getOrNull(1)?.text() != null) {
                        for ((index, j) in vp.withIndex()) {
                            if (index == 1) chart.lecturer =
                                j.text() /*println("lecturer: ${j.text()}")*/
                            if (index == 4) chart.subject =
                                j.text() /*println("subject: ${j.text()}")*/
                            if (index == 6) chart.totalHours =
                                j.text() /*println("allHours: ${j.text()}")*/
                            if (index == 9) chart.hoursLeft =
                                j.text() /*println("hoursLeft: ${j.text()}")*/
                        }
                        val img = vp.select("img")
                        val width = img.attr("width").toFloatOrNull()
                        if (width != null && width < 100F) {
                            chart.percent = (width.div(100F))
                        } else {
                            chart.percent = 1.0F
                        }
                        /*println("percent: $width")*/
                        /*println("chart: $chart")*/
                        chartsList.add(chart)
                    }
                }
            }
            return chartsList
        } catch (_: Exception) {
            return emptyList()
        }
    }
}