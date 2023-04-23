package ru.blays.AppUpdater.web.downloader

import android.content.Context
import android.os.Environment
import android.util.Log
import androidx.core.content.FileProvider
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.IOException
import ru.blays.AppUpdater.Downloader
import ru.blays.AppUpdater.Installer
import java.io.File
import java.io.FileOutputStream
import kotlin.random.Random

class OkHttpDownloader(private val context: Context) : Downloader {

    internal object Status {
        const val START_REQUEST = "START_REQUEST"
        const val START_DOWNLOAD = "START_DOWNLOAD"
        const val END_DOWNLOAD = "END_DOWNLOAD"
        const val ERROR = "ERROR"
    }

    val progressLiveData = MutableLiveData<Float>()

    override fun downloadFile(url: String) {


        val client = OkHttpClient()

        val request = Request.Builder()
            .url(url = url)
            .build()

        CoroutineScope(Dispatchers.IO).launch {

            try {

                /*this@OkHttpDownloader.postValue(Status.START_REQUEST)*/

                client.newCall(request).execute().use { response ->
                    if (!response.isSuccessful) {
                        throw IOException(
                            "Запрос к серверу не был успешен:" +
                                    " ${response.code} ${response.message}"
                        )
                    }

                    val file = File(
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                        "update${Random.nextInt(from = 1, until = 1000)}.apk"
                    )
                    Log.d("OkHttpDownloader", "save to $file")

                    val uri = FileProvider.getUriForFile(context, "ru.blays.timetable.provider", file)
                    Log.d("OkHttpDownloader", "uri: $uri")

                    /*this@OkHttpDownloader.postValue(Status.START_DOWNLOAD)*/

                    val inputStream = response.body.byteStream()
                    val outputStream = FileOutputStream(file)


                    val fileSize = response.body.contentLength()
                    Log.d("OkHttpDownloader", "file size: $fileSize")

                    val buffer = ByteArray(1024)
                    var bytesRead: Int
                    var totalBytesRead: Long = 0

                    while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                        outputStream.write(buffer, 0, bytesRead)
                        totalBytesRead += bytesRead
                        val progress: Float = (totalBytesRead.toFloat() / fileSize.toFloat())
                        progressLiveData.postValue(progress)
                    }
                    inputStream.close()
                    outputStream.close()
                    /*this@OkHttpDownloader.postValue(Status.END_DOWNLOAD)*/
                    Installer(context).install(uri)
                }
            } catch (e: IOException) {
                /*this@OkHttpDownloader.postValue(Status.ERROR)*/
                println("Ошибка подключения: $e")
            }
        }
    }
}