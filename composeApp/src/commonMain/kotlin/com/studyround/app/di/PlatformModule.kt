package com.studyround.app.di

import com.russhwolf.settings.Settings
import org.koin.core.module.Module

typealias Credentials = Settings

expect val platformModule: Module

const val NAMED_SETTINGS = "settings"
const val NAMED_CREDENTIALS = "credentials"
