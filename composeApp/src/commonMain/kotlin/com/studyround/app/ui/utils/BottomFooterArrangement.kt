package com.studyround.app.ui.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.unit.Density

/**
 * An [Arrangement.Vertical] implementation that arranges composables vertically such that all
 * composables are aligned to the top, except for the last composable, which is aligned to the bottom.
 *
 * This arrangement is especially useful in scenarios where you want the last composable (e.g., a footer)
 * to be pegged to the bottom of the container, regardless of the vertical space available.
 *
 * Usage:
 * ```kotlin
 * Column(
 *     verticalArrangement = remember { BottomFooterArrangement },
 *     modifier = Modifier.fillMaxHeight()
 * ) {
 *     // Composables here...
 * }
 * ```
 */
val BottomFooterArrangement = object : Arrangement.Vertical {
    override fun Density.arrange(
        totalSize: Int,
        sizes: IntArray,
        outPositions: IntArray,
    ) {
        var currentOffset = 0
        sizes.forEachIndexed { index, size ->
            if (index == sizes.lastIndex) {
                outPositions[index] = totalSize - size
            } else {
                outPositions[index] = currentOffset
                currentOffset += size
            }
        }
    }
}
