package ru.blays.AppUpdater.web.api

import okhttp3.OkHttpClient
import okhttp3.Request
import okio.IOException
import ru.blays.AppUpdater.dataClasses.GetInfoResult

class HttpClient : GetDataFromApi {
    override suspend fun get(): GetInfoResult {

        var status = false
        var json = ""

        val client = OkHttpClient()

        val request = Request.Builder()
            .url("https://raw.githubusercontent.com/B1ays/KAT-Timetable/Stable/update.json")
            .build()

        try {
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    throw IOException("Запрос к серверу не был успешен:" +
                            " ${response.code} ${response.message}")
                }
                status = true
                // пример получения конкретного заголовка ответа
                println("Server: ${response.header("Server")}")
                // вывод тела ответа
                /*Log.d("HTTP_request_log", response.body.string())*/
                json = response.body.string()
            }
        } catch (e: IOException) {
            println("Ошибка подключения: $e");
        }
        return GetInfoResult(json, status)
    }
}