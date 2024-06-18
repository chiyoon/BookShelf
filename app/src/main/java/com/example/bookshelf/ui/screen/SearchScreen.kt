package com.example.bookshelf.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.bookshelf.ui.component.TopBar

@Composable
fun SearchScreen() {
    Scaffold(
        topBar = { TopBar(content = "Search") }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            // TODO : Search Screen Body
        }
    }
}

@Composable
@Preview
fun SearchScreenPreview() {
    SearchScreen()
}