package com.example.medicmado4.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.ViewModelProvider
import com.example.medicmado4.ui.components.AppBackButton
import com.example.medicmado4.ui.components.AppTextField
import com.example.medicmado4.ui.theme.MedicMADO4Theme
import com.example.medicmado4.ui.theme.descriptionColor
import com.example.medicmado4.viewmodel.LoginViewModel
import kotlinx.coroutines.delay

/*
Описание: Класс экрана проверки кода из email
Дата создания: 29.03.2023 08:44
Автор: Георгий Хасанов
 */
class CodeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MedicMADO4Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ScreenContent()
                }
            }
        }
    }

    /*
    Описание: Контент экрана проверки кода из email
    Дата создания: 29.03.2023 08:44
    Автор: Георгий Хасанов
     */
    @Composable
    fun ScreenContent() {
        val mContext = LocalContext.current
        val shared = this.getSharedPreferences("shared", Context.MODE_PRIVATE)

        val viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        var emailText by rememberSaveable { mutableStateOf(intent.getStringExtra("email").toString()) }

        var code1 by rememberSaveable { mutableStateOf("") }
        var code2 by rememberSaveable { mutableStateOf("") }
        var code3 by rememberSaveable { mutableStateOf("") }
        var code4 by rememberSaveable { mutableStateOf("") }

        var isLoading by rememberSaveable { mutableStateOf(false) }
        var isAlertVisible by rememberSaveable { mutableStateOf(false) }

        val token by viewModel.token.observeAsState()
        LaunchedEffect(token) {
            isLoading = false

            if (token != null) {
                with(shared.edit()) {
                    putString("token", token)
                    apply()
                }

                val intent = Intent(mContext, PasswordActivity::class.java)
                startActivity(intent)
            }
        }

        val errorMessage by viewModel.errorMessage.observeAsState()
        LaunchedEffect(errorMessage) {
            isLoading = false

            if (errorMessage != null) {
                isAlertVisible = true
            }
        }

        var timer by rememberSaveable { mutableStateOf(60) }

        LaunchedEffect(timer) {
            delay(1000)

            if (timer > 0) {
                timer -= 1
            } else {
                viewModel.sendCode(emailText)
                timer = 60
            }
        }

        Scaffold(
            topBar = {
                AppBackButton(modifier = Modifier.padding(start = 20.dp, top = 24.dp)) {
                    onBackPressed()
                }
            }
        ) { padding ->
            Box(modifier = Modifier
                .padding(padding)
                .fillMaxSize()) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .widthIn(max = 350.dp)
                ) {
                    Text(
                        text = "Введите код из E-mail",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.W600
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .widthIn(max = 350.dp)
                            .fillMaxWidth()
                    ) {
                        AppTextField(
                            value = code1,
                            onValueChange = {
                                if (it.length <= 1) {
                                    code1 = it
                                }
                            },
                            textStyle = TextStyle(fontSize = 20.sp, lineHeight = 0.sp, textAlign = TextAlign.Center),
                            modifier = Modifier.size(48.dp),
                            contentPadding = PaddingValues(0.dp)
                        )
                        Spacer(modifier = Modifier.widthIn(16.dp))
                        AppTextField(
                            value = code2,
                            onValueChange = {
                                if (it.length <= 1) {
                                    code2 = it
                                }
                            },
                            textStyle = TextStyle(fontSize = 20.sp, lineHeight = 0.sp, textAlign = TextAlign.Center),
                            modifier = Modifier.size(48.dp),
                            contentPadding = PaddingValues(0.dp)
                        )
                        Spacer(modifier = Modifier.widthIn(16.dp))
                        AppTextField(
                            value = code3,
                            onValueChange = {
                                if (it.length <= 1) {
                                    code3 = it
                                }
                            },
                            textStyle = TextStyle(fontSize = 20.sp, lineHeight = 0.sp, textAlign = TextAlign.Center),
                            modifier = Modifier.size(48.dp),
                            contentPadding = PaddingValues(0.dp)
                        )
                        Spacer(modifier = Modifier.widthIn(16.dp))
                        AppTextField(
                            value = code4,
                            onValueChange = {
                                if (it.length <= 1) {
                                    code4 = it
                                }

                                if (it.length == 1) {
                                    viewModel.checkCode(emailText, "$code1$code2$code3$code4")
                                }
                            },
                            textStyle = TextStyle(fontSize = 20.sp, lineHeight = 0.sp, textAlign = TextAlign.Center),
                            modifier = Modifier.size(48.dp),
                            contentPadding = PaddingValues(0.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Отправить код повторно можно будет через $timer секунд",
                        fontSize = 15.sp,
                        color = descriptionColor,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.widthIn(240.dp)
                    )
                }
            }
        }

        if (isLoading) {
            Dialog(
                onDismissRequest = {}
            ) {
                CircularProgressIndicator()
            }
        }

        if (isAlertVisible) {
            AlertDialog(
                onDismissRequest = { isAlertVisible = false },
                title = {
                    Text(text = "Ошибка")
                },
                text = {
                    Text(text = viewModel.errorMessage.value.toString())
                },
                buttons = {
                    TextButton(onClick = { isAlertVisible = false }) {
                        Text(text = "OK")
                    }
                }
            )
        }
    }
}