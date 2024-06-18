import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.bookshelf.ui.navigation.BookShelfNavigation
import com.example.bookshelf.ui.navigation.BookShelfNavigationConfigurations
import com.example.composetest.NavigationItem

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    val mainNavItems = listOf(
        NavigationItem.New,
        NavigationItem.Search
    )

    Scaffold(
        bottomBar = {
            BookShelfNavigation(
                navController = navController,
                items = mainNavItems
            )
        }
    ) { innerPadding ->
        BookShelfNavigationConfigurations(navController = navController, innerPadding)
    }
}

@Composable
@Preview
fun MainScreenPreview() {
    MainScreen()
}