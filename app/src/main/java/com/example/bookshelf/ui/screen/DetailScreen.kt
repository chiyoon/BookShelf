package com.example.bookshelf.ui.screen

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.bookshelf.R
import com.example.bookshelf.presentation.viewmodel.DetailViewModel
import com.example.bookshelf.ui.component.BookDetailRow
import com.example.bookshelf.ui.component.BookDetailTextRow
import com.example.bookshelf.ui.component.FullScreenLoading
import com.example.bookshelf.ui.component.Rating
import com.example.bookshelf.ui.component.SquareButton
import com.example.bookshelf.ui.component.SquareButtonType
import com.example.bookshelf.ui.component.TopBar
import com.example.bookshelf.ui.theme.Typography
import org.unbescape.html.HtmlEscape

@Composable
fun DetailScreen(
    isbn13: String,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.getBooks(isbn13)

        // TODO: Collect 가 아닌 다른 방법으로 구현 가능한가?
        viewModel.isSaved.collect {
            if (it == true) {
                Toast.makeText(context, "메모가 저장되었습니다", Toast.LENGTH_SHORT).show()
            } else if (it == false) {
                Toast.makeText(context, "메모가 저장에 실패하였습니다", Toast.LENGTH_SHORT).show()
            }
        }
    }

    val keyboard = LocalSoftwareKeyboardController.current

    val bookDetail by viewModel.bookDetail.collectAsState(initial = null)

    val scrollState = rememberScrollState()

    val memo by viewModel.memo.collectAsState()

    val memoState = rememberSaveable(memo) { mutableStateOf(memo) }

    val isLoading by viewModel.isLoading.collectAsState()

    fun saveMemo() {
        bookDetail?.let {
            viewModel.updateMemo(it.isbn13, memoState.value)
        }

        keyboard?.hide()
    }

    Scaffold(
        topBar = { TopBar(content = "Book detail") }
    ) { innerPadding ->
        if (isLoading) {
            FullScreenLoading()
        }

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

                BookDetailTextRow(key = "Price", value = bookDetail.price)
                Rating(rating = bookDetail.rating)
                BookDetailTextRow(key = "Author", value = bookDetail.authors)
                BookDetailTextRow(key = "Publisher", value = bookDetail.publisher)
                BookDetailTextRow(key = "Published", value = bookDetail.year)
                BookDetailTextRow(key = "Pages", value = bookDetail.pages)
                BookDetailTextRow(key = "Language", value = bookDetail.language)
                BookDetailTextRow(key = "ISBN-10", value = bookDetail.isbn10)
                BookDetailTextRow(key = "ISBN-13", value = bookDetail.isbn13)
                BookDetailRow(key = "Web Link") {
                    SquareButton(SquareButtonType.Web) {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(bookDetail.url))

                        ContextCompat.startActivity(context, intent, null)
                    }
                }

                bookDetail.pdf?.let {
                    BookDetailRow(key = "PDF Link") {
                        it.forEach { (key, value) ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                SquareButton(type = SquareButtonType.Document) {
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(value))

                                    ContextCompat.startActivity(context, intent, null)
                                }
                                Spacer(modifier = Modifier.width(16.dp))
                                Text(text = key, style = Typography.bodyLarge)
                            }
                        }
                    }
                }

                Text(
                    text = "· Description",
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

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "· Memo",
                        style = Typography.titleMedium
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Save",
                            style = Typography.bodyLarge
                        )

                        Spacer(
                            modifier = Modifier.width(8.dp)
                        )

                        SquareButton(type = SquareButtonType.Save) {
                            saveMemo()
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                TextField(
                    value = memoState.value,
                    onValueChange = { memoState.value = it },
                    textStyle = Typography.bodyLarge,
                    singleLine = false,
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedLabelColor = Color.Transparent,
                        cursorColor = Color.Black,
                        textColor = Color.Black,
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = {
                        saveMemo()
                    }),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
@Preview
fun DetailScreenPreview() {
    DetailScreen(isbn13 = "9781617292576")
}
