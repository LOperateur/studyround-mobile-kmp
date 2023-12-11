package com.studyround.app.ui.composables.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ButtonElevation
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.toSize
import com.studyround.app.ui.theme.StudyRoundTheme

/**
 * Button that uses a [Brush] to specify a gradient,
 * while still able to maintain its disabled colour states.
 */
@Composable
fun GradientButton(
    onClick: () -> Unit,
    brush: Brush,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    elevation: ButtonElevation? = ButtonDefaults.elevation(),
    shape: Shape = MaterialTheme.shapes.small,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit,
) {
    var buttonSize by remember { mutableStateOf(DpSize.Zero) }
    val density = LocalDensity.current

    Button(
        enabled = enabled,
        modifier = modifier.onPlaced { with(density) { buttonSize = it.size.toSize().toDpSize() } },
        contentPadding = if (enabled) PaddingValues() else contentPadding, // overridden padding
        interactionSource = interactionSource,
        elevation = elevation,
        shape = shape,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            contentColor = StudyRoundTheme.colors.white,
            disabledContentColor = StudyRoundTheme.colors.black.copy(alpha = 0.75f),
            backgroundColor = Color.Transparent, // overridden colour
            disabledBackgroundColor = StudyRoundTheme.colors.gray,
        ),
    ) {
        Box(
            modifier = if (enabled) {
                Modifier.background(brush)
                    .requiredSizeIn(minWidth = buttonSize.width, minHeight = buttonSize.height)
                    .padding(contentPadding)
            } else Modifier,
            contentAlignment = Alignment.Center,
        ) {
            Row { content() }
        }
    }
}
