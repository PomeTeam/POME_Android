package com.teampome.pome.util.base

// out : 제네릭의 다형성 유지 (Array 사용 시)
// sealed class : Enum Class 대체 -> Class 내에서 여러 객체 생성을 위해(Enum의 변수는 싱글톤), When에서 else문을 사용하지 않기 위해 (제한을 둠)
sealed class ApiResponse<out T> {
    object Loading: ApiResponse<Nothing>()

    data class Success<out T>(
        val data: T
    ): ApiResponse<T>()

    data class Failure(
        val errorMessage: String,
        val code: String,
    ): ApiResponse<Nothing>()
}