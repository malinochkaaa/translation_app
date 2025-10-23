package com.example.translationapp.ui.translation_feature.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.translationapp.R

@Composable
fun TranslationScreen(
    outlinedText: String,
    text: String,
    onValueChange: (String) -> Unit,
    onTranslateButtonClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TranslationScreenOutlinedTextField(
            outlinedText,
            onValueChange,
        )
        Button(
            onClick = onTranslateButtonClick,
            modifier = Modifier.padding(vertical = 20.dp)
        ) {
            Text("Translate!")
        }
        TranslationScreenText(text)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TranslationScreenOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .width(250.dp)
            .height(200.dp)
            .background(
                color = colorResource(id = R.color.light_violet),
                shape = RoundedCornerShape(10.dp),
            ),
        shape = RoundedCornerShape(10.dp),
        placeholder = {
            Text(
                text = stringResource(R.string.translation_fragment_searched_word_hint_text),
                color = Color.Black,
            )
        },
        textStyle = LocalTextStyle.current.copy(
            fontSize = 16.sp,
            textAlign = TextAlign.Start,
        ),
        singleLine = false,
        maxLines = 1,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            disabledBorderColor = Color.Transparent,
            errorBorderColor = Color.Transparent,
            containerColor = colorResource(id = R.color.light_violet),
        )
    )
}

@Composable
private fun TranslationScreenText(
    text: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .width(250.dp)
            .height(200.dp)
            .background(
                color = colorResource(id = R.color.light_violet),
                shape = RoundedCornerShape(10.dp)
            )
            .padding(17.dp),
        contentAlignment = Alignment.TopStart
    ) {
        if (text.isEmpty()) {
            Text(
                text = stringResource(R.string.translation_fragment_translated_word_hint_text),
                color = Color.Black,
                fontSize = 16.sp
            )
        } else {
            Text(
                text = text,
                fontSize = 16.sp,
                color = Color.Black
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TranslationScreenPreview() {
    var outlinedText by remember { mutableStateOf("hello") }
    var text by remember { mutableStateOf("привет") }

    TranslationScreen(
        outlinedText = outlinedText,
        text = text,
        onValueChange = { text = it },
        onTranslateButtonClick = { println("btn pressed") }
    )
}