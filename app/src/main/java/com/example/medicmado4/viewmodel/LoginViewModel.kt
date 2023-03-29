package com.example.medicmado4.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicmado4.common.ApiService
import kotlinx.coroutines.launch

/*
Описание: Класс логики экранов входа в аккаунт
Дата создания: 29.03.2023 09:20
Автор: Георгий Хасанов
 */
class LoginViewModel: ViewModel() {
    val errorMessage = MutableLiveData<String>()
    val responseCode = MutableLiveData<Int>()
    val token = MutableLiveData<String>()

    /*
    Описание: Метод отправки кода на почту
    Дата создания: 29.03.2023 09:22
    Автор: Георгий Хасанов
     */
    fun sendCode(email: String) {
        errorMessage.value = null
        responseCode.value = null
        token.value = null

        viewModelScope.launch {
            try {
                val apiService = ApiService.getInstance()

                val json = apiService.sendCode(email)

                if (json.code() == 200) {
                    responseCode.value = 200
                } else {
                    errorMessage.value = json.code().toString()
                }
            } catch (e: Exception) {
                errorMessage.value = e.message.toString()
            }
        }
    }

    /*
    Описание: Метод проверки кода из почты
    Дата создания: 29.03.2023 09:29
    Автор: Георгий Хасанов
     */
    fun checkCode(email: String, code: String) {
        errorMessage.value = null
        responseCode.value = null
        token.value = null

        viewModelScope.launch {
            try {
                val apiService = ApiService.getInstance()

                val json = apiService.checkCode(email, code)

                if (json.code() == 200) {
                    token.value = json.body()!!.get("token").toString()
                } else {
                    errorMessage.value = json.code().toString()
                }
            } catch (e: Exception) {
                errorMessage.value = e.message.toString()
            }
        }
    }
}