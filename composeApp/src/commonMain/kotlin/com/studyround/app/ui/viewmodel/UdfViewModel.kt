package com.studyround.app.ui.viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import co.touchlab.kermit.Logger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlin.random.Random
import kotlin.random.nextInt

abstract class UdfViewModel<STATE_T, EVENT_T> : ScreenModel {

    private val id = Random.nextInt(10_000..99_999).toString()

    init {
        Logger.d(tag = TAG, messageString = " >> init [$id][${this::class.simpleName}]")
    }

    abstract val viewState: StateFlow<STATE_T>

    abstract fun processEvent(event: EVENT_T)

    override fun onDispose() {
        super.onDispose()
        Logger.d(tag = TAG, messageString = " << dispose [$id][${this::class.simpleName}]")
    }

    companion object {
        private const val TAG = "UdfViewModel"
    }
}

interface WithEffects<EFFECT_T> {
    val viewEffects: Flow<EFFECT_T>
}
