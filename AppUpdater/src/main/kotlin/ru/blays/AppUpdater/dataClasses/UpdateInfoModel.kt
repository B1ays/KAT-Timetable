package ru.blays.AppUpdater.dataClasses

import kotlinx.serialization.Serializable

@Serializable
data class UpdateInfoModel(
    val versionName: String,
    val versionCode: Int,
    val changed: Array<String>,
    val added: Array<String>,
    val deleted: Array<String>,
    val url: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UpdateInfoModel

        if (versionName != other.versionName) return false
        if (versionCode != other.versionCode) return false
        if (!changed.contentEquals(other.changed)) return false
        if (!added.contentEquals(other.added)) return false
        if (!deleted.contentEquals(other.deleted)) return false
        if (url != other.url) return false

        return true
    }

    override fun hashCode(): Int {
        var result = versionName.hashCode()
        result = 31 * result + versionCode
        result = 31 * result + changed.contentHashCode()
        result = 31 * result + added.contentHashCode()
        result = 31 * result + deleted.contentHashCode()
        result = 31 * result + url.hashCode()
        return result
    }
}