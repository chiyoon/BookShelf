package com.example.bookshelf.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.bookshelf.ui.component.TopBar

@Composable
fun NewScreen() {
    Scaffold(
        topBar = { TopBar(content = "What's New?") }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            // TODO : New Screen Body
        }
    }
}

@Composable
@Preview
fun NewScreenPreview() {
    NewScreen()
}