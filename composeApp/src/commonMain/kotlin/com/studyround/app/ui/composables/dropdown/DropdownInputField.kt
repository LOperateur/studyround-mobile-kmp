package com.studyround.app.ui.composables.dropdown

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.studyround.app.ui.composables.input.innerShadow
import com.studyround.app.ui.theme.StudyRoundTheme
import org.jetbrains.compose.resources.painterResource
import studyround.composeapp.generated.resources.Res
import studyround.composeapp.generated.resources.*

data class DropdownItem<T : Any>(
    val index: Int, // To handle duplicates
    val text: String,
    val value: T,
)

@Composable
fun <T : Any> DropdownInputField(
    modifier: Modifier = Modifier,
    text: String,
    items: List<DropdownItem<T>>,
    selectedItem: DropdownItem<T>?,
    onItemSelected: (DropdownItem<T>) -> Unit,
    focusManager: FocusManager = LocalFocusManager.current,
    textColor: Color = StudyRoundTheme.colors.deviation_tone4_tone5,
    backgroundColor: Color = StudyRoundTheme.colors.deviation_white_primary0,
) {
    var isExpanded by remember { mutableStateOf(false) }
    val iconRotation = animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f,
        label = "",
    )
    val iconId = Res.drawable.ic_expand_more

    // Wrapping the drop down in a Box so it draws it below the anchor view
    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(28.dp))
                .innerShadow(false, RoundedCornerShape(28.dp))
                .background(color = backgroundColor)
                .clickable {
                    focusManager.clearFocus()
                    isExpanded = !isExpanded
                },
        ) {
            Row(
                modifier = Modifier
                    .padding(start = 16.dp, end = 20.dp, top = 14.dp, bottom = 14.dp)
                    .align(Alignment.Center),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 18.dp),
                ) {
                    Text(
                        text = text,
                        style = StudyRoundTheme.typography.bodySmall,
                        color = textColor,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                    )
                }
                Icon(
                    modifier = Modifier.rotate(iconRotation.value),
                    painter = painterResource(iconId),
                    contentDescription = null,
                    tint = textColor,
                )
            }
        }

        DropdownMenu(
            modifier = Modifier.wrapContentWidth(),
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false },
        ) {
            items.forEach { item ->
                val isSelected = selectedItem == item
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = if (isSelected) {
                                StudyRoundTheme.colors.deviation_secondary1_secondary3
                            } else {
                                Color.Transparent
                            }
                        )
                        .clickable {
                            isExpanded = false
                            onItemSelected(item)
                        }
                        .padding(vertical = 8.dp, horizontal = 16.dp),
                    text = item.text,
                    style = StudyRoundTheme.typography.bodySmall,
                )
            }
        }
    }
}
