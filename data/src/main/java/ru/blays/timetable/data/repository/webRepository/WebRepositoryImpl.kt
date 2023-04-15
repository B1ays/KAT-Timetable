package ru.blays.timetable.data.repository.webRepository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import ru.blays.timetable.domain.repository.WebRepositoryInterface

const val serviceLink = "http://service.aviakat.ru:4256/"

class WebRepositoryImpl() : WebRepositoryInterface {

    override suspend fun getHTMLBody(href: String): Document? = withContext(Dispatchers.IO) {
        return@withContext Jsoup.connect(serviceLink + href).get()
    }

}