package com.arjuna.inbrief.ui.navigation

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.arjuna.inbrief.ui.screens.Article
import com.arjuna.inbrief.ui.screens.ArticleCategoryScreen
import com.arjuna.inbrief.ui.screens.CategoriesScreen
import com.arjuna.inbrief.ui.screens.HomeScreen
import com.arjuna.inbrief.ui.screens.SavedScreen
import com.arjuna.inbrief.ui.screens.SearchScreen

@SuppressLint("UnrememberedGetBackStackEntry")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavGraph(navController: NavHostController,paddingValues: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = NavItems.Home.route,
        modifier = Modifier.padding(paddingValues)
    ){

        composable(NavItems.Home.route){
//            backStackEntry ->
//            val articleViewModel: ArticleViewModel = hiltViewModel(backStackEntry)
            //value is updating in home screen so we are saying to create article vm when home screen is in back stack and value is updating from home screen
            HomeScreen(navController)
        }
        composable(NavItems.Search.route) { SearchScreen(navController) }
        composable(NavItems.Categories.route) { CategoriesScreen(navController) }
        composable(NavItems.Saved.route) { SavedScreen(navController) }
        composable(NavItems.Article.route){
//            val parentEntry = remember { navController.getBackStackEntry(NavItems.Home.route) } // taking the instance of vm of homescreen
//            val articleViewModel : ArticleViewModel = hiltViewModel(parentEntry) //“Give me the same ArticleViewModel that is attached to Home’s back stack”
            Article()
        }
        composable (NavItems.ArticleCategoryScreen.route){ ArticleCategoryScreen(navController) }
    }


}