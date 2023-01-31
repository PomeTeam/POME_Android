package com.teampome.pome.util

import androidx.navigation.NavController
import androidx.navigation.NavDirections

/**
 *  navigation 연속 호출을 방지하기 위한 메소드
 */
fun NavController.safeNavigate(direction: NavDirections) {
    currentDestination?.getAction(direction.actionId)?.run { navigate(direction) }
}