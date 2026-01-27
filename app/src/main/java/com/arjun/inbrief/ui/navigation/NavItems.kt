package com.arjun.inbrief.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Article
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavItems(
    val route : String,
    val name : String,
    val icon : ImageVector
){
    object Home : NavItems("home","Home", Icons.Default.Home)
    object Categories : NavItems("categories","Categories", Icons.AutoMirrored.Filled.List)
    object Search : NavItems("search","Search", Icons.Default.Search)
    object Saved : NavItems("saved","Saved", Icons.Default.Bookmarks)
    object Article : NavItems(route = "goToArticle", name = "Article", icon = Icons.AutoMirrored.Filled.Article)
    object ArticleCategoryScreen : NavItems(route = "goToArticleCategoryScreen", name = "ArticleCategoryScreen", icon = Icons.AutoMirrored.Default.Article)
}