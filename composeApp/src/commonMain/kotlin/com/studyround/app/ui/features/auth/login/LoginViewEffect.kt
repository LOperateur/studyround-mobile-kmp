package com.studyround.app.ui.features.auth.login

import com.studyround.app.utils.AppString

interface LoginViewEffect

data class ShowAlert(val message: AppString) : LoginViewEffect
