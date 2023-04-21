package ru.blays.AppUpdater

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.aurora.store.data.installer.Installer

class DownloadCompletedReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent?.action == "android.intent.action.DOWNLOAD_COMPLETE") {
            val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1L)
            if(id != -1L) {
                val downloadManager = context?.getSystemService(DownloadManager::class.java)
                val uri = downloadManager?.getUriForDownloadedFile(id)
                if (uri != null) {
                    Installer(context = context).run {
                        install(
                            uri = uri
                        )
                    }
                    Log.i( "AppUpdater","Download with ID $id finished!")
                } else {
                    Log.w("AppUpdater", "Download failed")
                }
            }
        }
    }
}