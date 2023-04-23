package ru.blays.AppUpdater.installer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInstaller
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Build
import android.util.Log

private const val TAG = "AppInstaller"

@Suppress("DEPRECATION")
class InstallReceiver : BroadcastReceiver() {


    override fun onReceive(context: Context, intent: Intent) {

        when (val status = intent.getIntExtra(PackageInstaller.EXTRA_STATUS, -1)) {
            PackageInstaller.STATUS_PENDING_USER_ACTION -> {

                Log.d(TAG, "Received status: STATUS_PENDING_USER_ACTION")

                val activityIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) intent.getParcelableExtra(Intent.EXTRA_INTENT, Intent::class.java)
                else intent.getParcelableExtra(Intent.EXTRA_INTENT)

                if (activityIntent != null) {
                    context.startActivity(activityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
                }
            }
            PackageInstaller.STATUS_SUCCESS -> {
                ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100)
                    .startTone(ToneGenerator.TONE_PROP_ACK)
                Log.d(TAG, "Received status: success")
            }
            else -> {

                Log.d(TAG, "Received status: error")

                val msg = intent.getStringExtra(PackageInstaller.EXTRA_STATUS_MESSAGE)

                Log.e(TAG, "received $status and $msg")
            }
        }
    }
}
