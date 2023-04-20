package ru.blays.AppUpdater.web.api

import com.google.gson.Gson
import ru.blays.AppUpdater.dataClasses.UpdateInfoModel

class JsonSerializer {

    fun fromJsonToClass(json: String): UpdateInfoModel? {
        return Gson().fromJson(json, UpdateInfoModel::class.java)
    }
}