package com.studyround.app.ui.features.dashboard.courses.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.studyround.app.ui.theme.StudyRoundTheme

@Composable
fun CourseDetailsBottomSheet() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = StudyRoundTheme.colors.deviation_tone1_primary1)
            .verticalScroll(rememberScrollState())
    ) {

    }
}
