package com.example.bookshelf.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bookshelf.ui.Model.Book
import com.example.bookshelf.ui.component.BookCard
import com.example.bookshelf.ui.component.TopBar

@Composable
fun NewScreen() {
    val list = (1..100).map {
        Book(
            "An Introduction to C & GUI Programming, 2nd Edition",
            "Architecting, Designing, and Deploying on the Snowflake Data Cloud",
            "9781098103828",
            "$58.90",
            "https://itbook.store/img/books/9781098103828.png",
            "https://itbook.store/books/9781098103828"
        )
    }

    Scaffold(
        topBar = { TopBar(content = "What's New?") }
    ) { innerPadding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(list.size) { index ->
                BookCard(list[index])
            }
        }
    }
}

@Composable
@Preview
fun NewScreenPreview() {
    NewScreen()
}