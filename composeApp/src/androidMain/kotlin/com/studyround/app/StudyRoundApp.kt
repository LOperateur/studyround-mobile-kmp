package com.studyround.app

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.studyround.app.di.initKoinAndroid
import com.studyround.app.platform.AndroidApplicationComponent
import org.koin.android.ext.koin.androidContext

class StudyRoundApp : Application(), Application.ActivityLifecycleCallbacks {

    var currentActivity: Activity? = null
        private set

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(this)

        initKoinAndroid(
            appComponent = AndroidApplicationComponent()
        ) {
            androidContext(this@StudyRoundApp)
        }
    }

    override fun onActivityCreated(activity: Activity, bundle: Bundle?) = Unit
    override fun onActivityStarted(activity: Activity) = Unit
    override fun onActivityResumed(activity: Activity) {
        currentActivity = activity
    }
    override fun onActivityPaused(activity: Activity) = Unit
    override fun onActivityStopped(activity: Activity) = Unit
    override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle) = Unit
    override fun onActivityDestroyed(activity: Activity) {
        if (currentActivity == activity) {
            currentActivity = null
        }
    }
}
