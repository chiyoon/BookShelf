package com.example.bookshelf.ui.component

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat.startActivity
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.bookshelf.ui.Model.Book
import com.example.bookshelf.ui.theme.Typography

@Composable
fun BookCard(book: Book) {
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(book.image)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxWidth()
        )
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
        val annotatedUrl = buildAnnotatedString {
            val text = "Web link"
            val startIndex = 0
            val endIndex = startIndex + text.length

            append(text)

            addStyle(
                style =  SpanStyle(
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
//        Text(
//            text = book.isbn13,
//            style = Typography.bodySmall
//        )
    }
}

@Composable
@Preview
fun BookCardPreview() {
    BookCard(book = Book(
        "An Introduction to C & GUI Programming, 2nd Edition",
        "Architecting, Designing, and Deploying on the Snowflake Data Cloud",
        "9781098103828",
        "$58.90",
        "https://itbook.store/img/books/9781098103828.png",
        "https://itbook.store/books/9781098103828"
    ))
}