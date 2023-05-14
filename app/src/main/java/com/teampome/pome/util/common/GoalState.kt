package com.teampome.pome.util.common

sealed class GoalState {
    object Empty: GoalState()
    object InProgress: GoalState()
    object End: GoalState()
}