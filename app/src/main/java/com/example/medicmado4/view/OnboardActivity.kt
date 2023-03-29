package com.example.medicmado4.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medicmado4.R
import com.example.medicmado4.ui.components.OnboardComponent
import com.example.medicmado4.ui.theme.MedicMADO4Theme
import com.example.medicmado4.ui.theme.secondaryColor
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

/*
Описание: Класс приветсвенного экрана
Дата создания: 29.03.2023 08:45
Автор: Георгий Хасанов
 */
class OnboardActivity : ComponentActivity() {
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
    Описание: Контент приветсвенного экрана
    Дата создания: 29.03.2023 08:45
    Автор: Георгий Хасанов
     */
    @OptIn(ExperimentalPagerApi::class)
    @Composable
    fun ScreenContent() {
        val mContext = LocalContext.current
        val shared = this.getSharedPreferences("shared", Context.MODE_PRIVATE)

        val imageArray = listOf(
            painterResource(id = R.drawable.onboard_image_1),
            painterResource(id = R.drawable.onboard_image_2),
            painterResource(id = R.drawable.onboard_image_3),
        )

        var buttonText by rememberSaveable { mutableStateOf("Пропустить") }

        val pagerState = rememberPagerState()

        LaunchedEffect(pagerState) {
            snapshotFlow { pagerState.currentPage }.collect {
                buttonText = if (it != 2) {
                    "Пропустить"
                } else {
                    "Завершить"
                }
            }
        }

        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, top = 5.dp)
            ) {
                Text(
                    text = buttonText,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W600,
                    color = secondaryColor,
                    modifier = Modifier.clickable {
                        with(shared.edit()) {
                            putBoolean("isFirstEnter", false)
                            apply()
                        }

                        val intent = Intent(mContext, LoginActivity::class.java)
                        startActivity(intent)
                    }
                )
                Image(
                    painter = painterResource(id = R.drawable.onboard_logo),
                    contentDescription = ""
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
            HorizontalPager(
                count = 3,
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
            ) {
                when (it) {
                    0 -> {
                        OnboardComponent(
                            title = "Анализы",
                            text = "Экспресс сбор и получение проб"
                        )
                    }
                    1 -> {
                        OnboardComponent(
                            title = "Уведомления",
                            text = "Вы быстро узнаете о результатах"
                        )
                    }
                    2 -> {
                        OnboardComponent(
                            title = "Мониторинг",
                            text = "Наши врачи всегда наблюдают за вашими показателями здоровья"
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                for (i in 0..2) {
                    Box(modifier = Modifier
                        .padding(4.dp)
                        .size(13.dp)
                        .clip(CircleShape)
                        .background(if (i == pagerState.currentPage) secondaryColor else Color.White)
                        .border(1.dp, secondaryColor, CircleShape)
                    )
                }
            }
            Spacer(modifier = Modifier.height(60.dp))
            Image(
                painter = imageArray[pagerState.currentPage],
                contentDescription = "",
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .height(270.dp)
                    .fillMaxSize()
                    .padding(bottom = 60.dp)
            )
        }
    }
}