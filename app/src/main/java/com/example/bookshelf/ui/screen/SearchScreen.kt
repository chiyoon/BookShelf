package com.example.bookshelf.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.bookshelf.R
import com.example.bookshelf.presentation.viewmodel.SearchViewModel
import com.example.bookshelf.ui.component.TopBar
import com.example.bookshelf.ui.theme.Typography

@Composable
fun SearchScreen(
    navController: NavHostController,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val keyboard =  LocalSoftwareKeyboardController.current

    val item = viewModel.searchBookList.collectAsLazyPagingItems()

    val textState = remember {
        mutableStateOf("")
    }

    Scaffold(
        topBar = { TopBar(content = "Search") }
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.padding(innerPadding)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(Color.LightGray, shape = CircleShape)
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(
                    value = textState.value,
                    onValueChange = { textState.value = it },
                    textStyle = Typography.titleSmall,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = {
                        viewModel.getSearch(textState.value)
                        keyboard?.hide()
                    }),
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    painter = painterResource(id = R.drawable.ic_search_black), // 화살표 아이콘 리소스를 여기에 추가하세요
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            viewModel.getSearch(textState.value)
                            keyboard?.hide()
                        }
                )
            }
            LazyColumn {
                items(item.itemCount) { index ->
                    item[index]?.let { book -> Text(text = book.title) }
                }
            }
        }
    }
}

@Composable
@Preview
fun SearchScreenPreview() {
    val navController = rememberNavController()
    SearchScreen(navController)
}