package com.studyround.app.ui.composables.alert

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.studyround.app.ui.theme.StudyRoundTheme
import org.jetbrains.compose.resources.DrawableResource
import studyround.composeapp.generated.resources.Res
import studyround.composeapp.generated.resources.*

sealed class AlertBannerType {
    @get:Composable
    abstract val color: Color
    abstract val icon: DrawableResource

    data object Error : AlertBannerType() {
        override val color: Color
            @Composable get() = StudyRoundTheme.colors.danger

        override val icon: DrawableResource = Res.drawable.ic_error
    }

    data object Success : AlertBannerType() {
        override val color: Color
            @Composable get() = StudyRoundTheme.colors.success

        override val icon: DrawableResource = Res.drawable.ic_check_circle
    }
}
