package com.studyround.app.ui.features.survey

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.studyround.app.ui.composables.buttons.CircularIconButton
import com.studyround.app.ui.composables.dropdown.DropdownInputField
import com.studyround.app.ui.composables.dropdown.DropdownItem
import com.studyround.app.ui.composables.input.InputField
import com.studyround.app.ui.composables.selector.TextRadioButton
import com.studyround.app.ui.theme.StudyRoundTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import studyround.composeapp.generated.resources.Res
import studyround.composeapp.generated.resources.*

@Composable
fun RegSurveyFormContent(
    modifier: Modifier = Modifier,
    viewState: RegSurveyViewState,
    textFieldState: RegSurveyTextFieldState,
    showCta: Boolean,
    eventProcessor: (RegSurveyViewEvent) -> Unit,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.BottomCenter,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
        ) {

            Spacer(modifier = Modifier.height(64.dp))

            Text(
                text = stringResource(Res.string.occupation_question),
                fontFamily = StudyRoundTheme.typography.montserratFont,
                color = StudyRoundTheme.colors.deviation_primary1_white,
                style = StudyRoundTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Normal),
            )

            DropdownInputField(
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp, bottom = 32.dp),
                text = viewState.occupationSelection?.text
                    ?: stringResource(Res.string.please_select_placeholder),
                items = viewState.occupations.mapIndexed { index, it ->
                    DropdownItem(index, it.getString(), it)
                },
                selectedItem = viewState.occupationSelection,
                onItemSelected = { eventProcessor(OccupationDropdownItemSelected(it)) },
            )

            AnimatedVisibility(
                visible = viewState.occupationSelection != null,
                // delay the fade in animation by 300ms
                enter = expandVertically() + fadeIn(animationSpec = tween(delayMillis = 300))
            ) {

                Column {
                    Text(
                        text = if (viewState.isStudentSelection) stringResource(Res.string.grade_question)
                        else stringResource(Res.string.job_title_question),
                        fontFamily = StudyRoundTheme.typography.montserratFont,
                        color = StudyRoundTheme.colors.deviation_primary1_white,
                        style = StudyRoundTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Normal),
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp, bottom = 32.dp)
                    ) {
                        if (viewState.isStudentSelection) {
                            DropdownInputField(
                                modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp),
                                text = viewState.gradeSelection?.text
                                    ?: stringResource(Res.string.please_select_placeholder),
                                items = viewState.grades.mapIndexed { index, it ->
                                    DropdownItem(index, it.getString(), it)
                                },
                                selectedItem = viewState.gradeSelection,
                                onItemSelected = { eventProcessor(GradeDropdownItemSelected(it)) },
                            )
                        } else {
                            InputField(
                                modifier = Modifier.fillMaxWidth(),
                                text = textFieldState.professionText,
                                singleLine = true,
                                placeholder = {
                                    Text(
                                        text = stringResource(Res.string.job_title_hint),
                                        style = StudyRoundTheme.typography.bodySmall
                                            .copy(fontWeight = FontWeight.Normal),
                                        color = StudyRoundTheme.colors.deviation_tone4_tone5
                                            .copy(alpha = 0.6f),
                                    )
                                },
                                onValueChange = { eventProcessor(JobTitleTextChanged(it)) },
                            )
                        }
                    }
                }
            }

            Text(
                text = stringResource(Res.string.awareness_question),
                fontFamily = StudyRoundTheme.typography.montserratFont,
                color = StudyRoundTheme.colors.deviation_primary1_white,
                style = StudyRoundTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Normal),
            )

            Column {
                viewState.awarenessSources.forEach { source ->
                    val sourceText = source.getString()
                    TextRadioButton(
                        text = sourceText,
                        modifier = Modifier.padding(top = 8.dp).fillMaxWidth(),
                        selected = viewState.awarenessSource == sourceText,
                        onClick = { eventProcessor(AwarenessSourceChanged(sourceText)) },
                    )
                }
            }

            Spacer(modifier = Modifier.height(64.dp))
        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Bottom,
        ) {
            if (showCta) {
                CircularIconButton(
                    modifier = Modifier.size(64.dp),
                    iconPadding = PaddingValues(0.dp),
                    painter = painterResource(Res.drawable.ic_arrow_forward),
                    iconColor = StudyRoundTheme.colors.white,
                    showLoading = viewState.submissionLoading,
                ) {
                    eventProcessor(SubmitButtonClicked)
                }
            }
        }
    }
}
