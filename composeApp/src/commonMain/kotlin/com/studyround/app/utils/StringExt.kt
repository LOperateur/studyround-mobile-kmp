package com.studyround.app.utils

/**
 * Pattern to check if an email is valid
 */
const val EMAIL_REGEX = "[a-zA-Z0-9-.!#$%&'*+–/=?^_`{|}~]{1,64}@[a-zA-Z0-9-]{1,187}\\.[a-zA-Z]{2,}"

/**
 * Pattern to check if a StudyRound username is valid
 */
const val USERNAME_REGEX = "^[a-zA-Z0-9_.-]+\$"

/**
 * @return true if the string is a valid email address
 */
fun String.isValidEmail(): Boolean = EMAIL_REGEX.toRegex().matches(this)

/**
 * @return true if the string is a StudyRound username
 */
fun String.isValidUsername(): Boolean = USERNAME_REGEX.toRegex().matches(this)
