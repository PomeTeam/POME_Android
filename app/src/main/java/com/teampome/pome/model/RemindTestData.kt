package com.teampome.pome.model

data class RemindTestData(
    val contentItems: List<RemindTestItem>,
)

data class RemindTestItem(
    val mainCategory: String,
    val goalCardItem: GoalCardItem,
    val contentCardItem: List<ContentCardItem>
)

data class GoalCardItem(
    val categories: List<String>,
    val goal: String
)

data class ContentCardItem(
    val firstThink: String,
    val lastThink: String,
    val money : String,
    val contentText: String,
    val name: String,
    val time: String,
    val friendEmotions: List<String>
)