package ru.blays.AppUpdater.web.api

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.IOException
import ru.blays.AppUpdater.dataClasses.GetInfoResult

class HttpClient : GetDataFromApi {

    private val updateInfoJsonUrl = "https://raw.githubusercontent.com/B1ays/KAT-Timetable/Stable/update.json"

    override suspend fun get(): GetInfoResult {

        var status = false
        var json = ""

        val client = OkHttpClient()

        val request = Request.Builder()
            .url(updateInfoJsonUrl)
            .build()

        try {
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    throw IOException(
                        "Запрос к серверу не был успешен:" +
                        " ${response.code} ${response.message}"
                    )
                }
                status = true
                json = response.body.string()
            }
        } catch (e: IOException) {
            Log.w("Update info api:","Ошибка подключения: $e");
        }
        return GetInfoResult(json, status)
    }
}