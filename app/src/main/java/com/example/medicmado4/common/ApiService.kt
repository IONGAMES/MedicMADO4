package com.example.medicmado4.common

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

/*
Описание: Интерфейс класса связи приложения с сервером
Дата создания: 29.03.2023 09:23
Автор: Георгий Хасанов
 */
interface ApiService {
    /*
    Описание: Метод отправки кода на почту
    Дата создания: 29.03.2023 09:27
    Автор: Георгий Хасанов
     */
    @Headers(
        "accept: application/json"
    )
    @POST("sendCode")
    suspend fun sendCode(@Header("email") email: String): Response<JsonObject>

    /*
    Описание: Метод проверки кода из почты
    Дата создания: 29.03.2023 09:27
    Автор: Георгий Хасанов
     */
    @Headers(
        "accept: application/json"
    )
    @POST("signin")
    suspend fun checkCode(@Header("email") email: String, @Header("code") code: String): Response<JsonObject>

    companion object {
        var apiService: ApiService? = null

        /*
        Описание: Метод инициализации класса ApiService
        Дата создания: 29.03.2023 09:23
        Автор: Георгий Хасанов
         */
        fun getInstance(): ApiService {
            if (apiService == null) {
                apiService = Retrofit.Builder()
                    .baseUrl("https://medic.madskill.ru/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiService::class.java)
            }

            return apiService!!
        }
    }
}