package com.example.bookshelf.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.bookshelf.R
import com.example.bookshelf.presentation.viewmodel.DetailViewModel
import com.example.bookshelf.ui.component.BookDetailRow
import com.example.bookshelf.ui.component.Rating
import com.example.bookshelf.ui.component.TopBar
import com.example.bookshelf.ui.theme.Typography
import org.unbescape.html.HtmlEscape

@Composable
fun DetailScreen(
    isbn13: String,
    viewModel: DetailViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.getBooks(isbn13)
    }

    val context = LocalContext.current

    val bookDetail by viewModel.bookDetail.collectAsState(initial = null)

    val scrollState = rememberScrollState()

    Scaffold(
        topBar = { TopBar(content = "Book detail") }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            bookDetail?.let { bookDetail ->
                Text(
                    text = bookDetail.title,
                    style = Typography.titleLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                )

                if (bookDetail.subtitle.isNotEmpty()) {
                    Text(
                        text = bookDetail.subtitle,
                        style = Typography.titleSmall,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }

                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(bookDetail.image)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .height(250.dp)
                        .clip(shape = RoundedCornerShape(16.dp)),
                    placeholder = painterResource(id = R.drawable.ic_image_placeholder),
                    error = painterResource(id = R.drawable.ic_image_placeholder)
                )

                BookDetailRow(key = "Price", value = bookDetail.price)
                Rating(rating = bookDetail.rating)
                BookDetailRow(key = "Author", value = bookDetail.authors)
                BookDetailRow(key = "Publisher", value = bookDetail.publisher)
                BookDetailRow(key = "Published", value = bookDetail.year)
                BookDetailRow(key = "Pages", value = bookDetail.pages)
                BookDetailRow(key = "Language", value = bookDetail.language)
                BookDetailRow(key = "ISBN-10", value = bookDetail.isbn10)
                BookDetailRow(key = "ISBN-13", value = bookDetail.isbn13)

                Text(
                    text = "Description",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    style = Typography.titleMedium
                )

                Text(
                    text = HtmlEscape.unescapeHtml(bookDetail.desc),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    style = Typography.bodyLarge,
                    textAlign = TextAlign.Justify
                )
            }
        }
    }
}

@Composable
@Preview
fun DetailScreenPreview() {
    DetailScreen(isbn13 = "9781617292576")
}
