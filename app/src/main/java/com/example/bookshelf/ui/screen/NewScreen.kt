package com.example.bookshelf.ui.screen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.bookshelf.R
import com.example.bookshelf.presentation.model.Book
import com.example.bookshelf.ui.component.BookCard
import com.example.bookshelf.ui.component.TopBar
import com.example.bookshelf.presentation.viewmodel.NewScreenViewModel

@Composable
fun NewScreen(
    navController: NavHostController,
    viewModel: NewScreenViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val list by viewModel.newBookList.collectAsState(initial = List(10) { Book.placeholder })
    val isConnected by viewModel.isConnected.collectAsState(initial = true)

    val toastString = stringResource(id = R.string.toast_not_connected)

    LaunchedEffect(Unit) {
        viewModel.getNew()
    }

    LaunchedEffect(isConnected) {
        if (!isConnected) {
            Toast.makeText(context, toastString, Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        topBar = { TopBar(content = "What's New?") }
    ) { innerPadding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(list.size) { index ->
                BookCard(list[index], modifier = Modifier.clickable {
                    navController.navigate("detail/" + list[index].isbn13)
                })
            }
        }
    }
}

@Composable
@Preview
fun NewScreenPreview() {
    val navController = rememberNavController()
    NewScreen(navController)
}