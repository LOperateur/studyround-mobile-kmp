package com.studyround.app.ui.composables.common.appbar

import com.studyround.app.ui.composables.dropdown.DropdownItem
import com.studyround.app.utils.AppString
import com.studyround.app.utils.AppStrings

data class AppBarViewState(
    val isMenuOpened: Boolean,
    val avatarUrl: String?,
    val darkModePreference: Boolean?,
) {
    val destinationMenuItems = listOf(
        DropdownItem(
            labelResource = AppString(AppStrings.MENU_PROFILE),
            value = AppBarNavDestination.PROFILE,
        ),
        DropdownItem(
            labelResource = AppString(AppStrings.MENU_RESULTS),
            value = AppBarNavDestination.RESULTS,
        ),
        DropdownItem(
            labelResource = AppString(AppStrings.MENU_FAQS),
            value = AppBarNavDestination.FAQS,
        ),
        DropdownItem(
            labelResource = AppString(AppStrings.MENU_TRANSACTIONS),
            value = AppBarNavDestination.TRANSACTIONS,
        ),
    )
}
