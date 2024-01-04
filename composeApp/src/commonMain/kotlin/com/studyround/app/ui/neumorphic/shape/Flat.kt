package com.studyround.app.ui.neumorphic.shape

import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import com.studyround.app.ui.neumorphic.NeuStyle
import com.studyround.app.ui.neumorphic.drawBackgroundShadows

class Flat(override val cornerShape: CornerShape) : NeuShape(cornerShape) {
    override fun draw(drawScope: ContentDrawScope, style: NeuStyle) {
        drawScope.drawBackgroundShadows(this, style)
        drawScope.drawContent()
    }
}
