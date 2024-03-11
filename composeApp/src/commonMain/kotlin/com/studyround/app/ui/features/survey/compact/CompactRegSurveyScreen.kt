package com.studyround.app.ui.features.survey.compact

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.studyround.app.ui.composables.common.StudyRoundBackground
import com.studyround.app.ui.composables.common.StudyRoundTextLogo
import com.studyround.app.ui.features.survey.RegSurveyFormContent
import com.studyround.app.ui.features.survey.RegSurveyTextFieldState
import com.studyround.app.ui.features.survey.RegSurveyViewEvent
import com.studyround.app.ui.features.survey.RegSurveyViewState

@Composable
fun CompactRegSurveyScreen(
    viewState: RegSurveyViewState,
    textFieldState: RegSurveyTextFieldState,
    eventProcessor: (RegSurveyViewEvent) -> Unit,
) {
    StudyRoundBackground()

    Column(modifier = Modifier.fillMaxSize().systemBarsPadding().imePadding()) {
        StudyRoundTextLogo(
            modifier = Modifier.padding(
                vertical = 8.dp,
                horizontal = 16.dp,
            ),
        )

        RegSurveyFormContent(
            modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
            showCta = true,
            eventProcessor = eventProcessor,
        )
    }
}
