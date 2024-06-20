package com.example.bookshelf.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.bookshelf.ui.component.TopBar

@Composable
fun DetailScreen(isbn13: String) {
    Scaffold(
        topBar = { TopBar(content = "Book detail") }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            Text(text = isbn13)
        }
    }
}

@Composable
@Preview
fun DetailScreenPreview() {
    DetailScreen(isbn13 = "1234567890")
}
