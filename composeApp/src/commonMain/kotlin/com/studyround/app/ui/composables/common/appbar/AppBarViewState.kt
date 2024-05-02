package com.studyround.app.ui.composables.common.appbar

import com.studyround.app.ui.composables.dropdown.DropdownItem

data class AppBarViewState(
    val isMenuOpened: Boolean,
    val avatarUrl: String?,
) {
    val destinationMenuItems = listOf(
        DropdownItem(0, "Profile", value = AppBarNavDestination.PROFILE),
        DropdownItem(1, "Results", value = AppBarNavDestination.RESULTS),
        DropdownItem(2, "FAQs", value = AppBarNavDestination.FAQS),
        DropdownItem(3, "Transactions", value = AppBarNavDestination.TRANSACTIONS),
    )
}
