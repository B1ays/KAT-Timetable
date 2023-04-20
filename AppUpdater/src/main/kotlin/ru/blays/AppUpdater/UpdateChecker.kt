package ru.blays.AppUpdater

import android.content.Context

class UpdateChecker(private val context: Context) {

    fun downloadAndInstall(url: String) {

        AndroidDownloader(context = context).run {
            downloadFile(url)
        }
    }
}