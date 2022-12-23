package com.teampome.pome.model

/**
 *  RemindCategory를 컨트롤하기 위해 만든 내부에서 사용하기 위한 데이터
 *  @param category : 카테고리 이름
 *  @param isSelected : 선택여부 확인
 */
data class RemindCategoryData(
    val category: String,
    var isSelected: Boolean? = false
)
