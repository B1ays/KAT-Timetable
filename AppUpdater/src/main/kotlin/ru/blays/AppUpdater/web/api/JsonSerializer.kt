package ru.blays.AppUpdater.web.api

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import ru.blays.AppUpdater.dataClasses.UpdateInfoModel

class JsonSerializer {

    fun fromJsonToClass(json: String): UpdateInfoModel {
        return Json.decodeFromString(string = json)
    }
}