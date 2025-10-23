package com.example.translationapp.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.translationapp.ui.entities.TranslationDetailsData

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TranslationsListScreen(
    emptyListText: String,
    items: List<TranslationDetailsData>?,
    onAddClick: () -> Unit,
    onLikeButtonClick: (TranslationDetailsData) -> Unit,
    onRemoveButtonClick: (TranslationDetailsData) -> Unit,
) {
    Scaffold(
        contentWindowInsets = WindowInsets(0.dp),
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClick
            ) {
                Icon(
                    modifier = Modifier
                        .width(28.dp)
                        .height(28.dp),
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add translation",
                )
            }
        }
    ) {
        if (items == null || items.isEmpty()) {
            TranslationEmptyListText(emptyListText)
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 12.dp),
            ) {
                items(items, key = { it.translationId }) { item ->
                    TranslationsListScreenCard(
                        searchedWordText = item.searchedWord,
                        foundWordText = item.translatedWord,
                        isLiked = item.isFavorite,
                        onLikeButtonClick = { onLikeButtonClick(item) },
                        onRemoveButtonClick = { onRemoveButtonClick(item) },
                    )

                }
            }
        }
    }

}

@Composable
private fun TranslationsListScreenCard(
    searchedWordText: String,
    foundWordText: String,
    isLiked: Boolean,
    onLikeButtonClick: () -> Unit,
    onRemoveButtonClick: () -> Unit,
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
        ) {
            Row(
                modifier = Modifier.align(Alignment.Center),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = searchedWordText,
                    color = Color.Gray,
                    fontSize = 18.sp
                )
                Icon(
                    modifier = Modifier
                        .width(16.dp)
                        .height(16.dp),
                    contentDescription = "Arrow",
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    tint = Color.Gray,
                )
                Text(
                    text = foundWordText,
                    color = Color.Gray,
                    fontSize = 18.sp
                )
            }
            Row(
                modifier = Modifier.align(Alignment.CenterEnd),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(
                    modifier = Modifier
                        .width(18.dp)
                        .height(18.dp),
                    onClick = onLikeButtonClick,
                ) {
                    Icon(
                        contentDescription = "Favorite",
                        imageVector = if (isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    )
                }
                IconButton(
                    modifier = Modifier
                        .width(18.dp)
                        .height(18.dp),
                    onClick = onRemoveButtonClick,
                ) {
                    Icon(
                        contentDescription = "Remove",
                        imageVector = Icons.Default.Delete,
                    )
                }
            }
        }
    }
}

@Composable
private fun TranslationEmptyListText(
    emptyListText: String,
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = emptyListText,
            color = Color.Gray,
            fontSize = 24.sp,
        )
    }
}
