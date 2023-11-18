package com.studyround.app

import android.app.Application
import com.studyround.app.di.initKoin
import com.studyround.app.platform.AndroidPlatformComponents
import org.koin.android.ext.koin.androidContext

class StudyRoundApp : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin(
            platformComponents = AndroidPlatformComponents()
        ) {
            androidContext(this@StudyRoundApp)
        }
    }
}
