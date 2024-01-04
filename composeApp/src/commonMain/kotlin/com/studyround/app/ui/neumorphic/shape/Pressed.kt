package com.studyround.app.ui.neumorphic.shape

import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import com.studyround.app.ui.neumorphic.NeuStyle
import com.studyround.app.ui.neumorphic.drawForegroundShadows

class Pressed(override val cornerShape: CornerShape) : NeuShape(cornerShape) {
    override fun draw(drawScope: ContentDrawScope, style: NeuStyle) {
        drawScope.drawContent()
        drawScope.drawForegroundShadows(this, style)
    }
}
