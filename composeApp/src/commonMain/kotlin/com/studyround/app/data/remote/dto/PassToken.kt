package com.studyround.app.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PassToken(@SerialName("pass_token") val passToken: String)
