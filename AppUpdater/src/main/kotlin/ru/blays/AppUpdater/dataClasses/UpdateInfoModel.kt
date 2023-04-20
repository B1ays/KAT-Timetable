package ru.blays.AppUpdater.dataClasses

data class UpdateInfoModel(
    val versionName: String,
    val versionCode: Int,
    val changelog: String,
    val url: String
)