package com.studyround.app.utils

import androidx.core.bundle.Bundle

fun Map<String, Any>.mapToBundle(): Bundle {
    val bundle = Bundle()
    for ((key, value) in this) {
        when (value) {
            is Int -> bundle.putInt(key, value)
            is Long -> bundle.putLong(key, value)
            is String -> bundle.putString(key, value)
            is Boolean -> bundle.putBoolean(key, value)
            is Float -> bundle.putFloat(key, value)
            is Double -> bundle.putDouble(key, value)
            // Add more types if needed
            else -> throw IllegalArgumentException("Unsupported bundle component for key $key")
        }
    }
    return bundle
}
