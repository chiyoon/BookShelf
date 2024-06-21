package com.example.bookshelf.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bookshelf.ui.theme.Typography

@Composable
fun Rating(rating: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Rating", modifier = Modifier.weight(1f), style = Typography.titleMedium)
        Spacer(modifier = Modifier.width(16.dp))
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val rating = if (rating > 5) {
                5
            } else if (rating < 0) {
                0
            } else {
                rating
            }
            repeat(rating) {
                Star(type = StarType.Full)
            }

            repeat(5 - rating) {
                Star(type = StarType.Empty)
            }
        }
    }
}

@Composable
@Preview
fun RatingPreview() {
    Rating(4)
}