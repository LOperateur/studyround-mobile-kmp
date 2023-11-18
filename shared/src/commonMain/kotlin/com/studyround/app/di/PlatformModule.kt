package com.studyround.app.di

import com.studyround.app.platform.Platform
import org.koin.core.module.Module

expect fun platformModule(platform: Platform): Module
