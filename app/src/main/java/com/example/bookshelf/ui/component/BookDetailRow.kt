package com.example.bookshelf.ui.component

import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import com.example.bookshelf.ui.theme.Typography

@Composable
fun BookDetailRow(key: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = key, modifier = Modifier.weight(1f), style = Typography.titleMedium)
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = value, modifier = Modifier.weight(1f), style = Typography.bodyLarge)
    }
}

@Composable
fun BookDetailRow(key: String, value: Int) {
    BookDetailRow(key = key, value = value.toString())
}

@Composable
@Preview
fun BookDetailRowPreview() {
    BookDetailRow("Hello", "World!")
}