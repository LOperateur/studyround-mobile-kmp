package com.studyround.app.ui.composables.selector

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonColors
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.studyround.app.ui.theme.StudyRoundTheme

@Composable
fun TextRadioButton(
    selected: Boolean,
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: RadioButtonColors = RadioButtonDefaults.colors()
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .clickable { onClick() },
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RadioButton(
            selected = selected,
            onClick = { /* Automatically applies the clickable padding */ onClick() },
            enabled = enabled,
            colors = colors,
        )

        Text(
            text = text,
            style = StudyRoundTheme.typography.bodySmall,
        )
    }
}
