package com.studyround.app.ui.neumorphic.shape

import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import com.studyround.app.ui.neumorphic.NeuStyle

abstract class NeuShape(open val cornerShape: CornerShape) {
    abstract fun draw(drawScope: ContentDrawScope, style: NeuStyle)
}
