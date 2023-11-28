package com.studyround.app.storage

import android.content.Context
import android.content.SharedPreferences
import com.studyround.app.utils.encryptedPreferences

object Preferences {
    fun createSettingsPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences("studyround_shared_prefs", Context.MODE_PRIVATE)
    }

    fun createCredentialsPrefs(context: Context): SharedPreferences {
        return context.encryptedPreferences("studyround_secret_shared_prefs")
    }
}
