package com.example.orderdeliver.data.base

import com.example.orderdeliver.data.models.ErrorResponseBody
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonEncodingException
import com.squareup.moshi.Moshi
import retrofit2.HttpException
import java.io.IOException

open class BaseRetrofitSource(moshi: Moshi) {

    private val errorAdapter = moshi.adapter(ErrorResponseBody::class.java)

    suspend fun <T> wrapRetrofitExceptions(block: suspend () -> T): T {
        return try {
            block()
        } catch (e: JsonDataException) {
            throw ParseJsonException(e)
        } catch (e: JsonEncodingException) {
            throw ParseJsonException(e)
        } catch (e: HttpException) {
            throw createServerException(e)
        } catch (e: IOException) {
            throw ConnectionException(e)
        } catch (e: Exception) {
            throw e
        }
    }

    private fun createServerException(e: HttpException): Exception {
        return try {
            val errorBody: ErrorResponseBody = errorAdapter.fromJson(
                e.response()!!.errorBody()!!.string()
            )!!
            BackendException(errorBody.message, e.code())
        } catch (e: Exception) {
            println(e)
            throw ParseJsonException(e)
        }
    }

}