package ru.blays.AppUpdater

import androidx.lifecycle.MutableLiveData

interface Downloader {
    fun downloadFile(url: String)
}