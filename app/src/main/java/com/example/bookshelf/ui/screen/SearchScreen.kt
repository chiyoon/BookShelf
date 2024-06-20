package com.example.bookshelf.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.bookshelf.presentation.viewmodel.NewScreenViewModel
import com.example.bookshelf.presentation.viewmodel.SearchViewModel
import com.example.bookshelf.ui.component.TopBar

@Composable
fun SearchScreen(
    navController: NavHostController,
    viewModel: SearchViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.getSearch()
    }

    val list = viewModel.searchBookList.collectAsState(initial = emptyList())

    Scaffold(
        topBar = { TopBar(content = "Search") }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            // TODO : Search Screen Body
        }
    }
}

@Composable
@Preview
fun SearchScreenPreview() {
    val navController = rememberNavController()
    SearchScreen(navController)
}