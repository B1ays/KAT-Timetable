package ru.blays.AppUpdater.dataClasses

data class UpdateInfoModel(
    val versionName: String,
    val versionCode: Int,
    val changed: String,
    val added: String,
    val deleted: String,
    val url: String
)