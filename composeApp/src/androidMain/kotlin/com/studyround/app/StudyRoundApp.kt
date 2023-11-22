package com.studyround.app

import android.app.Application
import com.studyround.app.di.initKoinAndroid
import com.studyround.app.platform.AndroidApplicationComponent
import org.koin.android.ext.koin.androidContext

class StudyRoundApp : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoinAndroid(
            appComponent = AndroidApplicationComponent(this)
        ) {
            androidContext(this@StudyRoundApp)
        }
    }
}
