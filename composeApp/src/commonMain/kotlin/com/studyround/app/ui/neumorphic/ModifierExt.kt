package com.studyround.app.ui.neumorphic

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.DrawModifier
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.studyround.app.ui.neumorphic.shape.Flat
import com.studyround.app.ui.neumorphic.shape.NeuShape
import com.studyround.app.ui.neumorphic.shape.RoundedCorner


/**
 * Class used to capture all the neumorphic style attributes.
 *
 * @param lightShadowColor Light shadow color.
 * @param darkShadowColor Dark shadow color.
 * @param shadowElevation Elevation or Depth.
 * @param shape Component shape.
 * @param lightSource Light-source direction used to draw light and dark shadow at different position.
 */
data class NeuAttrs(
    val lightShadowColor: androidx.compose.ui.graphics.Color,
    val darkShadowColor: androidx.compose.ui.graphics.Color,
    val shadowElevation: Dp,
    val shape: NeuShape,
    val lightSource: LightSource = LightSource.LEFT_TOP
)

/**
 * Extension method to create neumorphic ui on compose-ui components.
 *
 * @param neuAttrs object contains all the neumorphic style attributes.
 */
fun Modifier.neumorphic(neuAttrs: NeuAttrs) = this.then(
    Modifier.neumorphic(
        neuAttrs.lightShadowColor,
        neuAttrs.darkShadowColor,
        neuAttrs.shadowElevation,
        neuAttrs.shape,
        neuAttrs.lightSource,
    )
)

/**
 * Extension method to create neumorphic ui on compose-ui components.
 *
 * @param lightShadowColor Light shadow color.
 * @param darkShadowColor Dark shadow color.
 * @param shadowElevation Elevation or Depth.
 * @param shape Component shape.
 * @param lightSource Light-source direction used to draw light and dark shadow at different position.
 */
fun Modifier.neumorphic(
    lightShadowColor: androidx.compose.ui.graphics.Color,
    darkShadowColor: androidx.compose.ui.graphics.Color,
    shadowElevation: Dp = 4.dp,
    shape: NeuShape = Flat(RoundedCorner(0.dp)),
    lightSource: LightSource = LightSource.LEFT_TOP
) = this.then(
    object : DrawModifier {
        override fun ContentDrawScope.draw() {
            val style = NeuStyle(
                lightShadowColor = lightShadowColor,
                darkShadowColor = darkShadowColor,
                shadowElevation = shadowElevation,
                lightSource = lightSource
            )
            shape.draw(this, style)
        }
    }
)
