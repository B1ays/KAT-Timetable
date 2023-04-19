package ru.blays.AppUpdater

import android.content.Context
import android.net.Uri
import com.aurora.store.data.installer.NativeInstaller

class UpdateChecker(private val context: Context, private val applicationId: String) {

    fun checkUpdate() {

        AndroidDownloader(context = context).run {
            downloadFile("https://github.com/B1ays/KAT-Timetable/releases/download/1.1.0_build3/KAT.-.Timetable.1.1.0_build3.apk")
        }
    }

    private fun install(uri: Uri) {
        NativeInstaller(context = context).run {
            install(
            uri = uri,
            applicationId = applicationId
        )}
    }
}