package com.studyround.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import co.touchlab.kermit.Logger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlin.random.Random
import kotlin.random.nextInt

abstract class UdfViewModel<STATE_T, EVENT_T> : ViewModel() {

    private val id = Random.nextInt(10_000..99_999).toString()

    init {
        Logger.d(tag = TAG, messageString = " >> init [$id][${this::class.simpleName}]")
    }

    abstract val viewState: StateFlow<STATE_T>

    abstract fun processEvent(event: EVENT_T)

    override fun onCleared() {
        super.onCleared()
        Logger.d(tag = TAG, messageString = " << clear [$id][${this::class.simpleName}]")
    }

    companion object {
        private const val TAG = "UdfViewModel"
    }
}

interface WithEffects<EFFECT_T> {
    val viewEffects: Flow<EFFECT_T>
}
