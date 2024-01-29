package com.studyround.app.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Otp(@SerialName("otp_id") val otpId: String)
