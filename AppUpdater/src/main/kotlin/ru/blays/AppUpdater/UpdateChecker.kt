package ru.blays.AppUpdater

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import com.aurora.store.data.installer.NativeInstaller
import java.io.File

class UpdateChecker(private val context: Context, private val applicationId: String) {

    suspend fun checkUpdate() {
        val fileUri = FileProvider.getUriForFile(
            context,
            "ru.blays.timetable.provider",
            File(context.filesDir.path.plus("/test.apk")
            )
        )

        install(fileUri)
    }




    private fun install(uri: Uri) {
        NativeInstaller(context = context).run {
            install(
            uri = uri,
            applicationId = applicationId
        )}
    }
}