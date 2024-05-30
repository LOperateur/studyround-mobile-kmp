package com.studyround.app.ui.composables.common.appbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.studyround.app.ui.composables.common.RemoteImage
import com.studyround.app.ui.composables.dropdown.DropdownItem
import com.studyround.app.ui.theme.StudyRoundTheme
import org.jetbrains.compose.resources.painterResource
import studyround.composeapp.generated.resources.*

@Composable
fun StudyRoundAppBar(
    title: String,
    textColor: Color = StudyRoundTheme.colors.deviation_tone4_tone5,
    modifier: Modifier = Modifier,
    viewModel: AppBarViewModel,
    hideLogo: Boolean = false,
    onBackPressed: (() -> Unit)? = null,
    onNavDestinationClicked: (AppBarNavDestination) -> Unit = {},
) {
    val viewState by viewModel.viewState.collectAsState()
    val eventProcessor = viewModel::processEvent

    LaunchedEffect(Unit) {
        viewModel.viewEffects.collect { effect ->
            when (effect) {
                is RequestNavigate -> {
                    onNavDestinationClicked(effect.destination)
                }
            }
        }
    }

    Box(
        modifier = modifier
            .background(color = StudyRoundTheme.colors.deviation_white_primary0)
            .shadow(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = StudyRoundTheme.colors.deviation_white_primary0)
                .statusBarsPadding()
                .padding(
                    vertical = 8.dp,
                    horizontal = 16.dp,
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (onBackPressed != null) {
                IconButton(
                    modifier = Modifier.size(36.dp),
                    onClick = { onBackPressed() },
                ) {
                    Icon(
                        modifier = Modifier.height(24.dp),
                        painter = painterResource(Res.drawable.ic_arrow_back),
                        contentDescription = "Back",
                        tint = textColor,
                    )
                }
            } else {
                if (!hideLogo) {
                    Image(
                        modifier = Modifier.size(36.dp),
                        painter = painterResource(Res.drawable.studyround_logo),
                        contentDescription = "Logo",
                    )
                }
            }

            Text(
                modifier = Modifier.align(Alignment.CenterVertically)
                    .padding(start = 16.dp),
                text = title,
                color = textColor,
                style = StudyRoundTheme.typography.bodySmall,
            )

            Spacer(modifier = Modifier.weight(1f, fill = true))

            Box(
                modifier = Modifier.clip(CircleShape)
                    .clickable { eventProcessor(MenuToggled(true)) }) {

                RemoteImage(
                    url = viewState.avatarUrl.orEmpty(),
                    modifier = Modifier.size(32.dp),
                    placeholderPainter = painterResource(Res.drawable.ic_avatar),
                    placeholderTint = StudyRoundTheme.colors.gray,
                )

                DropdownMenu(
                    expanded = viewState.isMenuOpened,
                    onDismissRequest = { eventProcessor(MenuToggled(false)) }
                ) {
                    ThemeSwitcher(viewState.darkModePreference, eventProcessor)

                    viewState.destinationMenuItems.forEach {
                        StudyRoundAppBarMenuItem(
                            dropdownItem = it,
                            eventProcessor = eventProcessor,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ThemeSwitcher(
    darkModeSetting: Boolean?,
    eventProcessor: (AppBarViewEvent) -> Unit,
) {
    val isDarkMode = darkModeSetting ?: isSystemInDarkTheme()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minWidth = 192.dp)
            .background(color = StudyRoundTheme.colors.tone2.copy(alpha = 0.4f))
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier.size(20.dp),
            painter = painterResource(Res.drawable.ic_light_mode),
            tint = StudyRoundTheme.colors.gray,
            contentDescription = "Light mode",
        )

        Switch(
            modifier = Modifier.padding(horizontal = 8.dp),
            checked = isDarkMode,
            onCheckedChange = {
                eventProcessor(DarkModeToggled(it))
            },
        )

        Icon(
            modifier = Modifier.size(20.dp),
            painter = painterResource(Res.drawable.ic_dark_mode),
            tint = StudyRoundTheme.colors.gray,
            contentDescription = "Dark mode",
        )
    }
}

@Composable
private fun StudyRoundAppBarMenuItem(
    dropdownItem: DropdownItem<AppBarNavDestination>,
    eventProcessor: (AppBarViewEvent) -> Unit,
) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                eventProcessor(MenuToggled(false))
                eventProcessor(NavDestinationClicked(dropdownItem.value))
            }
            .padding(vertical = 8.dp, horizontal = 16.dp),
        text = dropdownItem.label(),
        style = StudyRoundTheme.typography.bodySmall
    )
}
