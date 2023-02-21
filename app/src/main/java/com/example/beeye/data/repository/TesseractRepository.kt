package com.example.beeye.data.repository

import android.graphics.Bitmap

interface TesseractRepository {
    fun getOCRText(bitmap: Bitmap): String?

    fun setTess(dataPath: String, lang: String)
}