package com.example.bookshelf.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.bookshelf.R

@Composable
fun WebButton(type: WebButtonType, onClick: () -> Unit) {
    val icon = when(type) {
        WebButtonType.Web -> R.drawable.ic_globe
        WebButtonType.Document -> R.drawable.ic_file
    }

    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            disabledContainerColor = Color.LightGray
        ),
        shape = RoundedCornerShape(8.dp),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 1.dp,
            pressedElevation = 0.dp
        ),
        modifier = Modifier.width(32.dp).height(32.dp).padding(0.dp),
        contentPadding = PaddingValues(0.dp)
    ) {
        Image(
            imageVector = ImageVector.vectorResource(id = icon),
            contentDescription = null,
            modifier = Modifier.size(20.dp).padding(0.dp)
        )
    }
}

enum class WebButtonType {
    Web, Document
}