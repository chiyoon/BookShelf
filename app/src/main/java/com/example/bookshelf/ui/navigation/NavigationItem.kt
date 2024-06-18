package com.example.composetest

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.bookshelf.R

sealed class NavigationItem(var route: String, @DrawableRes var icon: Int, @StringRes var title: Int) {
    data object New: NavigationItem("new", R.drawable.ic_home_black, R.string.nav_new)
    data object Search: NavigationItem("search", R.drawable.ic_search_black, R.string.nav_search)
}
