package com.studyround.app.di

import com.studyround.app.platform.PlatformComponents
import org.koin.core.module.Module

expect fun platformModule(platformComponent: PlatformComponents): Module
