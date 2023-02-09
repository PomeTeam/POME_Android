package com.teampome.pome.model.base

data class Pageable(
    val offset: Int,
    val pageNumber: Int,
    val pageSize: Int,
    val paged: Boolean,
    val sort: Sort,
    val unpaged: Boolean
)

data class Sort(
    val empty: Boolean,
    val sorted: Boolean,
    val unsorted: Boolean
)