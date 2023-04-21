package ru.blays.AppUpdater.dataClasses

import kotlinx.serialization.Serializable

@Serializable
data class UpdateInfoModel(
    val versionName: String,
    val versionCode: Int,
    val changed: String,
    val added: String,
    val deleted: String,
    val url: String
)