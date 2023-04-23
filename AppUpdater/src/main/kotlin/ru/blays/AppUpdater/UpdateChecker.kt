package ru.blays.AppUpdater

import android.util.Log
import androidx.core.app.ComponentActivity
import androidx.lifecycle.MutableLiveData
import ru.blays.AppUpdater.dataClasses.GetInfoResult
import ru.blays.AppUpdater.dataClasses.UpdateInfoModel
import ru.blays.AppUpdater.web.api.HttpClient
import ru.blays.AppUpdater.web.api.JsonSerializer
import ru.blays.AppUpdater.web.downloader.OkHttpDownloader

class UpdateChecker(private val context: ComponentActivity, private val versionCode: Int) {

    val downloadStatus = MutableLiveData<String>()

    val updateInfo = MutableLiveData<UpdateInfoModel>()

    lateinit var downloadProgress: MutableLiveData<Float>

    lateinit var status: MutableLiveData<String>

    var isUpdateAvailable = MutableLiveData<Boolean>()

    suspend fun check() {

        val result: GetInfoResult

        val httpClient = HttpClient()

        result = httpClient.get()

        if (result.status) {
           /* Log.d("AppUpdater", result.json.toString())*/
            try {
                val jsonSerializer = JsonSerializer()
                val jsonSerializerResult = jsonSerializer.fromJsonToClass(result.json ?: "")
                Log.d("serializationLog1", jsonSerializerResult.toString())
                updateInfo.postValue(jsonSerializerResult)
                /*Log.d("serializationLog2", updateInfo.value.toString())*/

                if (jsonSerializerResult.versionCode > versionCode) {
                    isUpdateAvailable.postValue(true)
                }

            } catch (e: Exception) {
                Log.w("SerializationLog", "$e")
            }
        }
    }

    fun downloadUpdate() {
        val okHttpDownloader = OkHttpDownloader(context = context)

        /*Log.d("OkHttpDownloader", "Start")*/

        downloadProgress = okHttpDownloader.progressLiveData

        status = okHttpDownloader.status

        okHttpDownloader.downloadFile(updateInfo.value!!.url, fileName = "КАТ-Расписание_${updateInfo.value!!.versionName}(${updateInfo.value!!.versionCode})")

    }
}