package ru.blays.AppUpdater

interface Downloader {
    fun downloadFile(url: String): Long
}