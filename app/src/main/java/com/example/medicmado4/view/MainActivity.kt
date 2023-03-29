package com.example.medicmado4.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.medicmado4.R
import com.example.medicmado4.ui.theme.MedicMADO4Theme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/*
Описание: Класс Splash экрана
Дата создания: 29.03.2023 08:36
Автор: Георгий Хасанов
 */
class MainActivity : ComponentActivity() {
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val mContext = LocalContext.current
            val shared = this.getSharedPreferences("shared", Context.MODE_PRIVATE)

            MedicMADO4Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.splash_bg),
                        contentDescription = "",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier.fillMaxSize()
                    )

                    Box(modifier = Modifier.fillMaxSize()) {
                        Image(
                            painter = painterResource(id = R.drawable.splash_logo),
                            contentDescription = "",
                            modifier = Modifier.fillMaxWidth().align(Alignment.Center).padding(56.dp)
                        )
                    }
                }
            }

            val scope = rememberCoroutineScope()
            scope.launch {
                delay(1000)

                if (shared.getBoolean("isFirstEnter", true)) {
                    val intent = Intent(mContext, OnboardActivity::class.java)
                    startActivity(intent)
                } else {
                    if (shared.getString("token", "") != "") {
                        val intent = Intent(mContext, PasswordActivity::class.java)
                        startActivity(intent)
                    } else {
                        val intent = Intent(mContext, LoginActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
        }
    }
}