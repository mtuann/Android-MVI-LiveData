/*
 * Copyright 2021 UET-AILAB
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.modelviewintent.default_act_mvi

import android.util.Log
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import okio.*
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.concurrent.TimeUnit



interface DefaultAPI  {

    @POST("get_file_dicom")
    suspend fun getFileDicom02(@Body data: RequestBody): Call<ResponseBody>

    @GET("study__patient_case")
    suspend fun getStudyCase(
        @Query("IDStudy") idStudy: String ) : ResponseBody


    interface ProgressListener {
        fun update(bytesRead: Long, contentLength: Long, done: Boolean)
    }

    companion object {
        const val TAG = "DefaultAPI"
        val BASE_URL = "http://68.183.186.28:5000/api/v1/"
        val interceptor = HttpLoggingInterceptor().apply {
//            this.level = HttpLoggingInterceptor.Level.BODY
        }



        class MyActClient(val listener: ProgressListener) {
            val client = OkHttpClient.Builder().apply {
                this.addInterceptor(interceptor)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(25, TimeUnit.SECONDS)
                    .addNetworkInterceptor { chain ->
                        val originalResponse: Response = chain.proceed(chain.request())
                        originalResponse.newBuilder()
                            .body(originalResponse.body?.let { ProgressResponseBody(it, progressListener = listener) })
                            .build()

                    }.build()
            }.build()

        }

        val clientNoListener = OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(25, TimeUnit.SECONDS)
                .addNetworkInterceptor { chain ->
                    val originalResponse: Response = chain.proceed(chain.request())
                    val downloadProgressListener = object : ProgressListener {
                        override fun update(bytesRead: Long, contentLength: Long, done: Boolean) {
                            Log.w(TAG, "Process to: ${bytesRead} ${contentLength} DONE: ${(100 * bytesRead) / contentLength} %")
                        }

                    }
                    originalResponse.newBuilder()
                        .body(originalResponse.body?.let { ProgressResponseBody(it, progressListener = downloadProgressListener) })
                        .build()

                }.build()
        }.addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }).build()


        fun create(listener: ProgressListener): DefaultAPI {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(MyActClient(listener).client)
                .build()
                .create(DefaultAPI::class.java)
        }

        fun createNoListener(): DefaultAPI {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(clientNoListener)
                .build()
                .create(DefaultAPI::class.java)

        }

    }

    class ProgressResponseBody(private val responseBody: ResponseBody, val progressListener: ProgressListener) : ResponseBody() {
        private var bufferedSource: BufferedSource? = null

        override fun contentType(): MediaType? {
            return responseBody.contentType()
        }

        override fun contentLength(): Long {
            return responseBody.contentLength()
        }

        override fun source(): BufferedSource {
            if (bufferedSource == null) {
                bufferedSource = source(responseBody.source()).buffer()
            }
            return bufferedSource!!
        }

        private fun source(source: Source): Source {
            return object: ForwardingSource(source) {
                var totalBytesRead = 0L

                override fun read(sink: Buffer, byteCount: Long): Long {
                    val bytesRead = super.read(sink, byteCount)
                    // read() returns the number of bytes read, or -1 if this source is exhausted.
                    totalBytesRead += if (bytesRead != -1L) bytesRead else 0
                    progressListener.update(totalBytesRead, responseBody.contentLength(), bytesRead == -1L)
                    return bytesRead
                }
            }
        }
    }
}
