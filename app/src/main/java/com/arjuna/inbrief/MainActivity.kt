package com.arjuna.inbrief

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.arjuna.inbrief.ui.UIState.SharedUiState
import com.arjuna.inbrief.ui.components.AppBottomBar
import com.arjuna.inbrief.ui.components.AppTopBar
import com.arjuna.inbrief.ui.navigation.AppNavGraph
import com.arjuna.inbrief.ui.navigation.NavItems
import com.arjuna.inbrief.ui.theme.InBriefTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
@RequiresApi(Build.VERSION_CODES.O)
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var sharedUiState: SharedUiState
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute by remember {
                derivedStateOf {
                    navBackStackEntry?.destination?.route ?: NavItems.Home.route
                }
            }
            var selectedItemName by remember { mutableStateOf("Home") }
            val selectCategoryTitle = sharedUiState.categoryTitle.collectAsState()
            val items = listOf(
                NavItems.Home,
                NavItems.Categories,
                NavItems.Search,
                NavItems.Saved
            )

            InBriefTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        AppTopBar(
                            title = if (currentRoute == NavItems.Article.route) "Article" else if (currentRoute == NavItems.ArticleCategoryScreen.route) selectCategoryTitle.value else selectedItemName,
                            showBackIcon = currentRoute == NavItems.Article.route || currentRoute == NavItems.ArticleCategoryScreen.route,
                            onClickBackIcon = { navController.popBackStack() }
                        )
                    },
                    bottomBar = {
                        if ((currentRoute != NavItems.Article.route) && (currentRoute != NavItems.ArticleCategoryScreen.route)) {
                            AppBottomBar(items = items, currentRoute = currentRoute, { item ->
                                selectedItemName = item.name
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        /*
                                    popUpTo = popup everything expect the root or start destination id means
                                                    Home → Details → Profile - backstack
                                                    Home → Details → Profile → Home   ❌ - before clicking on home again
                                                    Home - after clicking on home (removes all other screens from backstack)
                                     */
//                                        saveState = true // it saves ui state like scroll position, text field values, lazy list positions
                                    }
                                    launchSingleTop = true //launch mode.
                                    restoreState =
                                        true //Restore UI state when navigating back. in savestate we are saving state that saved state is restoring here
                                }
                            })
                        }
                    }
                ) { innerPadding ->
                    AppNavGraph(navController = navController, innerPadding)
                }
            }
        }
    }
}
