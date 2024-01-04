package com.studyround.app.ui.neumorphic

/**
 * Light source constants with [opposite] util function.
 */
enum class LightSource {
    LEFT_TOP,
    RIGHT_TOP,
    LEFT_BOTTOM,
    RIGHT_BOTTOM;

    fun opposite(): LightSource {
        return when (this) {
            LEFT_TOP -> RIGHT_BOTTOM
            RIGHT_TOP -> LEFT_BOTTOM
            LEFT_BOTTOM -> RIGHT_TOP
            RIGHT_BOTTOM -> LEFT_TOP
        }
    }
}
