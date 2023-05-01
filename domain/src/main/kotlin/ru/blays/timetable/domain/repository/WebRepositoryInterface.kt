package ru.blays.timetable.domain.repository

import org.jsoup.nodes.Document

interface WebRepositoryInterface {

    suspend fun getHTMLBody(href: String) : Document?

}