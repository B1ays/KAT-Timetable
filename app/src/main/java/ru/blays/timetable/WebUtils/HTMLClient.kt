package ru.blays.timetable.WebUtils

import kotlinx.coroutines.coroutineScope
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

// Aviakat services permanent link
const val serviceLink = "http://service.aviakat.ru:4256/"

class HTMLClient() {
    suspend fun getHTTP(href: String) : Document = coroutineScope {
        return@coroutineScope Jsoup.connect(serviceLink + href).get()
    }
}
