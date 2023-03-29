package com.example.medicmado4.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medicmado4.ui.theme.descriptionColor
import com.example.medicmado4.ui.theme.titleColor

/*
Описание: Компонент приветственного экрана
Дата создания: 29.03.2023 09:00
Автор: Георгий Хасанов
 */
@Composable
fun OnboardComponent(
    title: String,
    text: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .widthIn(max = 280.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.W600,
            color = titleColor,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(29.dp))
        Text(
            text = text,
            fontSize = 14.sp,
            color = descriptionColor,
            textAlign = TextAlign.Center
        )
    }
}