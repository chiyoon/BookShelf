package com.example.bookshelf.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bookshelf.R

@Composable
fun Star(type: StarType) {
    val drawable = when(type) {
        StarType.Full -> R.drawable.ic_star_full
        StarType.Empty -> R.drawable.ic_star_empty
    }

    Image(painter = painterResource(id = drawable), contentDescription = null, Modifier.size(20.dp))
}

enum class StarType {
    Full, Empty
}

@Composable
@Preview
fun StarPreview() {
    Star(type = StarType.Full)
}