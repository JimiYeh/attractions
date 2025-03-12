package com.example.attractions.repository

import androidx.annotation.Keep

/**
 * 封裝 API 請求的回應結果
 * @param T API 成功時返回的資料類型
 */
@Keep
sealed class NetworkResponse<out T : Any> {
    /**
     * API 請求成功的回應 (HTTP 狀態碼 2xx)
     * @param data API 返回的資料
     */
    data class Success<T : Any>(
        val data: T
    ) : NetworkResponse<T>()

    /**
     * API 請求失敗的回應 (HTTP 狀態碼非 2xx)
     * @param code HTTP 狀態碼
     * @param errorBody 錯誤回應內容
     * @param errorMessage 錯誤訊息
     */
    data class Error(
        val code: Int,
        val errorBody: String?,
        val errorMessage: String?
    ) : NetworkResponse<Nothing>()

    /**
     * API 請求過程中發生異常
     * @param exception 異常資訊
     * @param message 異常訊息
     */
    data class Exception(
        val exception: Throwable,
        val message: String? = exception.message
    ) : NetworkResponse<Nothing>()

    /**
     * 判斷是否為成功的回應
     */
    val isSuccess: Boolean get() = this is Success

    /**
     * 判斷是否為失敗的回應
     */
    val isError: Boolean get() = this is Error

    /**
     * 判斷是否發生異常
     */
    val isException: Boolean get() = this is Exception
}