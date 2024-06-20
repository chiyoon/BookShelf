package com.example.bookshelf.ui.component

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.bookshelf.R
import com.example.bookshelf.presentation.model.Book
import com.example.bookshelf.ui.theme.Typography

@Composable
fun BookRow(book: Book, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(book.image)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxHeight()
                .width(100.dp)
                .clip(shape = RoundedCornerShape(16.dp)),
            placeholder = painterResource(id = R.drawable.ic_image_placeholder),
            error = painterResource(id = R.drawable.ic_image_placeholder)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = book.title,
                style = Typography.titleSmall
            )

            if (book.subtitle.isNotEmpty()) {
                Text(
                    text = (book.subtitle),
                    style = Typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Text(
                text = book.price, style = Typography.bodyMedium
            )

            if (book.url.isNotEmpty()) {
                val annotatedUrl = buildAnnotatedString {
                    val text = "Web link"
                    val startIndex = 0
                    val endIndex = startIndex + text.length

                    append(text)

                    addStyle(
                        style = SpanStyle(
                            Color.Blue
                        ),
                        start = startIndex,
                        end = endIndex
                    )

                    addStringAnnotation(
                        tag = "URL",
                        annotation = book.url,
                        start = startIndex,
                        end = endIndex
                    )
                }
                ClickableText(
                    text = annotatedUrl,
                    style = Typography.bodySmall,
                    onClick = { _ ->
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(book.url))

                        startActivity(context, intent, null)
                    }
                )
            }
            Text(
                text = book.isbn13,
                style = Typography.bodySmall
            )
        }
    }
}

@Composable
@Preview
fun BookRowPreview() {
    BookRow(
        book = Book(
            "An Introduction to C & GUI Programming, 2nd Edition",
            "Architecting, Designing, and Deploying on the Snowflake Data Cloud",
            "9781098103828",
            "$58.90",
            "https://itbook.store/img/books/9781098103828.png",
            "https://itbook.store/books/9781098103828"
        )
    )
}