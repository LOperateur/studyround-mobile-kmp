package com.studyround.app.ui.neumorphic.shape

import androidx.compose.ui.unit.Dp

sealed class CornerShape

data object Oval : CornerShape()

data class RoundedCorner(val radius: Dp) : CornerShape()
