package com.example.beeye.common.ocr

import com.googlecode.tesseract.android.TessBaseAPI

object Tesseract {
    val tess: TessBaseAPI by lazy {
        TessBaseAPI()
    }
}