package com.example.medicmado4.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.ViewModelProvider
import com.example.medicmado4.ui.theme.MedicMADO4Theme
import com.example.medicmado4.R
import com.example.medicmado4.ui.components.AppButton
import com.example.medicmado4.ui.components.AppTextField
import com.example.medicmado4.ui.theme.descriptionColor
import com.example.medicmado4.ui.theme.strokeColor
import com.example.medicmado4.ui.theme.textColor
import com.example.medicmado4.viewmodel.LoginViewModel

/*
Описание: Класс экрана входа в аккаунт
Дата создания: 29.03.2023 08:44
Автор: Георгий Хасанов
 */
class LoginActivity : ComponentActivity() {
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
    Описание: Контент экрана входа в аккаунт
    Дата создания: 29.03.2023 08:45
    Автор: Георгий Хасанов
     */
    @Composable
    fun ScreenContent() {
        val mContext = LocalContext.current

        val viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        var emailText by rememberSaveable { mutableStateOf("") }

        var enabled by rememberSaveable { mutableStateOf(false) }

        var isLoading by rememberSaveable { mutableStateOf(false) }
        var isAlertVisible by rememberSaveable { mutableStateOf(false) }

        val responseCode by viewModel.responseCode.observeAsState()
        LaunchedEffect(responseCode) {
            isLoading = false

            if (responseCode == 200) {
                val intent = Intent(mContext, CodeActivity::class.java)
                intent.putExtra("email", emailText)

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

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .widthIn(max = 400.dp)
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ) {
            Row(
                modifier = Modifier
                    .widthIn(max = 400.dp)
                    .fillMaxWidth()
                    .padding(top = 60.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_hello),
                    contentDescription = "",
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.widthIn(16.dp))
                Text(
                    text = "Добро пожаловать!",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.W700
                )
            }
            Column(modifier = Modifier
                .widthIn(max = 400.dp)
                .fillMaxWidth()) {
                Spacer(modifier = Modifier.height(23.dp))
                Text(
                    text = "Войдите, чтобы пользоваться функциями приложения",
                    fontSize = 15.sp,
                    modifier = Modifier.widthIn(max = 400.dp)
                )
                Spacer(modifier = Modifier.height(60.dp))
                Text(
                    text = "Вход по E-mail",
                    fontSize = 14.sp,
                    color = textColor,
                    modifier = Modifier
                        .align(Alignment.Start)
                )
                Spacer(modifier = Modifier.height(4.dp))
                AppTextField(
                    modifier = Modifier
                        .widthIn(max = 400.dp)
                        .fillMaxWidth(),
                    value = emailText,
                    placeholder = {
                        Text(
                            text = "example@mail.ru",
                            fontSize = 15.sp,
                            color = Color.Black.copy(0.5f)
                        )
                    },
                    onValueChange = {
                        emailText = it

                        enabled = emailText.isNotEmpty()
                    }
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            AppButton(
                text = "Далее",
                enabled = enabled,
                modifier = Modifier
                    .widthIn(max = 400.dp)
                    .fillMaxWidth()
            ) {
                if (Regex("^[a-zA-Z0-9]*@[a-zA-Z0-9]*\\.[a-zA-Z]{2,}$").matches(emailText)) {
                    isLoading = true

                    viewModel.sendCode(emailText)
                } else {
                    Toast.makeText(mContext, "Неправильный формат E-Mail!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        Box(modifier = Modifier
            .widthIn(max = 400.dp)
            .fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .widthIn(max = 440.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 56.dp)
                    .align(Alignment.BottomCenter)
            ) {
                Text(
                    text = "Или войдите с помощью",
                    fontSize = 15.sp,
                    color = descriptionColor
                )
                Spacer(modifier = Modifier.height(16.dp))
                AppButton(
                    text = "Войти с Яндекс",
                    fontWeight = FontWeight.W500,
                    borderStroke = BorderStroke(1.dp, strokeColor),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                    color = Color.Black,
                    modifier = Modifier.widthIn(max = 440.dp).fillMaxWidth()
                ) {

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