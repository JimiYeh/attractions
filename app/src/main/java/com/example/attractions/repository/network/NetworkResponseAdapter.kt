package com.example.attractions.repository.network

import com.example.attractions.repository.NetworkResponse
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type


internal class NetworkResponseAdapter<T : Any>(
    private val successType: Type
) : CallAdapter<T, Call<NetworkResponse<T>>> {

    override fun responseType(): Type = successType

    override fun adapt(call: Call<T>): Call<NetworkResponse<T>> {
        return NetworkResponseCall(call)
    }
}