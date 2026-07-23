package com.example.util

import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import java.util.Locale

class TextToSpeechHelper(context: Context) : TextToSpeech.OnInitListener {
    private var tts: TextToSpeech? = TextToSpeech(context.applicationContext, this)
    private var isInitialized = false

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts?.setLanguage(Locale.GERMAN)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "German Language is missing or not supported.")
            } else {
                isInitialized = true
                tts?.setSpeechRate(0.85f) // Slightly slower rate for clear language learning pronunciation
            }
        } else {
            Log.e("TTS", "TextToSpeech initialization failed.")
        }
    }

    fun speakGerman(text: String) {
        if (isInitialized && text.isNotBlank()) {
            tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "GermanSpeechId")
        }
    }

    fun shutdown() {
        tts?.stop()
        tts?.shutdown()
    }
}
