package com.studyround.app.data.remote.request

enum class AuthType(val value: String) {
    VERIFY_EMAIL("auth_type_verify_email"),
    RESET_PASSWORD("auth_type_reset_password"),
}
