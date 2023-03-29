package com.example.medicmado4.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medicmado4.R
import com.example.medicmado4.ui.theme.*

/*
Описание: Стандартная кнопка приложения
Дата создания: 29.03.2023 09:36
Автор: Георгий Хасанов
 */
@Composable
fun AppButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: String,
    fontSize: TextUnit = 17.sp,
    fontWeight: FontWeight= FontWeight.W600,
    borderStroke: BorderStroke = BorderStroke(0.dp, strokeColor),
    color: Color = Color.White,
    contentPadding: PaddingValues = PaddingValues(16.dp),
    colors: ButtonColors = ButtonDefaults.buttonColors(backgroundColor = primaryColor, disabledBackgroundColor = primaryDisabledColor),
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        contentPadding = contentPadding,
        elevation = ButtonDefaults.elevation(0.dp),
        colors = colors,
        enabled = enabled,
        border = borderStroke,
        onClick = onClick
    ) {
        Text(
            text = text,
            fontSize = fontSize,
            fontWeight = fontWeight,
            color = color,
        )
    }
}

/*
Описание: Кнопка перехода на предыдущий экран
Дата создания: 29.03.2023 10:41
Автор: Георгий Хасанов
 */
@Composable
fun AppBackButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier.clickable{ onClick() },
        backgroundColor = inputColor,
        elevation = 0.dp
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = "",
            tint = textColor,
            modifier = Modifier.padding(6.dp)
        )
    }
}