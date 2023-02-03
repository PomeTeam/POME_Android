package com.teampome.pome.presentation.record

sealed class GoalState {
    object Empty: GoalState()
    object InProgress: GoalState()
    object End: GoalState()
}