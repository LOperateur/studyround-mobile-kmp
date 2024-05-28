package com.studyround.app.data.model.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Otp(@SerialName("otp_id") val otpId: Int)
