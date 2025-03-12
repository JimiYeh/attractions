package com.example.attractions.repository.network

import com.example.attractions.repository.NetworkResponse
import com.google.gson.Gson
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import kotlin.coroutines.cancellation.CancellationException

/**
 * 將 Retrofit 的 Call<T> 轉換為 Call<NetworkResponse<T>>
 * @param T 成功響應的數據類型
 * @property delegate 原始的 Call<T> 對象
 */
internal class NetworkResponseCall<T : Any>(
    private val delegate: Call<T>
) : Call<NetworkResponse<T>> {

    override fun enqueue(callback: Callback<NetworkResponse<T>>) {
        delegate.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val body = response.body()
                val code = response.code()
                val error = response.errorBody()

                if (response.isSuccessful) {
                    if (body != null) {
                        callback.onResponse(
                            this@NetworkResponseCall,
                            Response.success(NetworkResponse.Success(data = body))
                        )
                    } else {
                        callback.onResponse(
                            this@NetworkResponseCall,
                            Response.success(NetworkResponse.Exception(
                                exception = NullPointerException("Response body is null"),
                                message = "成功響應但數據為空"
                            ))
                        )
                    }
                } else {
                    val errorBody = when {
                        error == null -> null
                        error.contentLength() == 0L -> null
                        else -> try {
                            error.string()
                        } catch (ex: Exception) {
                            null
                        }
                    }

                    val errorMessage = try {
                        errorBody?.let {
                            Gson().fromJson(it, ErrorResponse::class.java)?.message
                        }
                    } catch (ex: Exception) {
                        null
                    }

                    callback.onResponse(
                        this@NetworkResponseCall,
                        Response.success(NetworkResponse.Error(
                            code = code,
                            errorBody = errorBody,
                            errorMessage = errorMessage
                        ))
                    )
                }
            }

            override fun onFailure(call: Call<T>, throwable: Throwable) {
                when (throwable) {
                    // 直接拋出 CancellationException
                    is CancellationException -> throw throwable
                    
                    // 其他異常包裝成 NetworkResponse.Exception
                    else -> {
                        callback.onResponse(
                            this@NetworkResponseCall,
                            Response.success(NetworkResponse.Exception(
                                exception = throwable,
                                message = throwable.message
                            ))
                        )
                    }
                }
            }
        })
    }

    override fun isExecuted(): Boolean = delegate.isExecuted

    override fun clone(): Call<NetworkResponse<T>> = NetworkResponseCall(delegate.clone())

    override fun isCanceled(): Boolean = delegate.isCanceled

    override fun cancel() = delegate.cancel()

    override fun execute(): Response<NetworkResponse<T>> {
        throw UnsupportedOperationException("NetworkResponseCall 不支持同步執行")
    }

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()
}