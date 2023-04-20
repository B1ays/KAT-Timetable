/*
 * Aurora Store
 *  Copyright (C) 2021, Rahul Kumar Patel <whyorean@gmail.com>
 *
 *  Aurora Store is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  Aurora Store is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Aurora Store.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.aurora.store.data.installer

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInstaller
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.documentfile.provider.DocumentFile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.blays.AppUpdater.installer.InstallReceiver
import ru.blays.AppUpdater.installer.InstallerBase

class Installer(context: Context) : InstallerBase(context) {

    override fun install(uri: Uri) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            Log.d("InstallerLog", "Session installer")
            CoroutineScope(Dispatchers.IO).launch { coroutineInstaller(uri) }
        } else {
            Log.d("InstallerLog", "Native installer")
            nativeInstaller(uri)
        }
    }


    @Suppress("DEPRECATION")
    private fun nativeInstaller(uri: Uri) {

        val intent = Intent(Intent.ACTION_INSTALL_PACKAGE)
        intent.data = uri
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_NEW_TASK

        intent.putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true)
        intent.putExtra(Intent.EXTRA_INSTALLER_PACKAGE_NAME, "com.android.vending")
        context.startActivity(intent)
    }

    private suspend fun coroutineInstaller(apkUri: Uri) =
        withContext(Dispatchers.IO) {

            val NAME = "mostly-unused"
            val PI_INSTALL = 3439

            val installer = context.packageManager.packageInstaller
            val resolver = context.contentResolver

            resolver.openInputStream(apkUri)?.use { apkStream ->
                val length = DocumentFile.fromSingleUri(context, apkUri)?.length() ?: -1
                val params = PackageInstaller.SessionParams(PackageInstaller.SessionParams.MODE_FULL_INSTALL)
                val sessionId = installer.createSession(params)
                val session = installer.openSession(sessionId)

                session.openWrite(NAME, 0, length).use { sessionStream ->
                    apkStream.copyTo(sessionStream)
                    session.fsync(sessionStream)
                }

                val intent = Intent(context, InstallReceiver::class.java)
                val pi = PendingIntent.getBroadcast(
                    context,
                    PI_INSTALL,
                    intent,
                    PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                )

                session.commit(pi.intentSender)
                session.close()
            }
        }
}