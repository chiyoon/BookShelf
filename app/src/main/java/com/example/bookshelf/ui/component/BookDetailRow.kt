package com.example.bookshelf.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
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
fun BookDetailRow(key: String, content: @Composable ColumnScope.() -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = key, modifier = Modifier.weight(1f), style = Typography.titleMedium)
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f), content = content, horizontalAlignment = Alignment.CenterHorizontally)
    }
}

@Composable
fun BookDetailTextRow(key: String, value: String) {
    BookDetailRow(key = key) {
        Text(text = value, modifier = Modifier.fillMaxWidth(), style = Typography.bodyLarge)
    }
}

@Composable
fun BookDetailTextRow(key: String, value: Int) {
    BookDetailTextRow(key = key, value = value.toString())
}

@Composable
@Preview
fun BookDetailTextRowPreview() {
    BookDetailTextRow("Hello", "World!")
}