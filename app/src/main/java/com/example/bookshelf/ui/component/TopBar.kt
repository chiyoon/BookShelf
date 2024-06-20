package com.example.bookshelf.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bookshelf.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(content: String) {
    TopAppBar(
        title = {
            Text(
                text = content,
                style = Typography.titleLarge,
            )
        },
        modifier = Modifier
            .statusBarsPadding()
            .shadow(4.dp)
            .background(Color.White)
    )
}

@Composable
@Preview
fun TopBarPreview() {
    TopBar(content = "Sample Text")
}