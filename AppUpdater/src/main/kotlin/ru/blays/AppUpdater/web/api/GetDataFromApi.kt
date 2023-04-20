package ru.blays.AppUpdater.web.api

import ru.blays.AppUpdater.dataClasses.GetInfoResult

interface GetDataFromApi {

    suspend fun get(): GetInfoResult

}