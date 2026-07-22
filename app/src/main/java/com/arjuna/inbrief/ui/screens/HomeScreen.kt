package com.arjuna.inbrief.ui.screens

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.arjuna.inbrief.domain.model.TopHeadLinesModel
import com.arjuna.inbrief.ui.navigation.NavItems
import com.arjuna.inbrief.ui.utils.timeAgoFromISO
import com.arjuna.inbrief.ui.utils.toDate
import com.arjuna.inbrief.ui.viewModel.HomeScreenViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    navController: NavController,
//    articleViewModel: ArticleViewModel = hiltViewModel(),
    homeViewModel: HomeScreenViewModel = hiltViewModel()
) {
//    val articleViewModel: ArticleViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        homeViewModel.loadTopHeadLines()
    }


    val context = LocalContext.current
    var isRefreshingByPullDown = homeViewModel.UiState.collectAsState().value.isLoading
    val pullToRefreshState = rememberPullToRefreshState()

    val coroutineScope = CoroutineScope(Dispatchers.Default)
    val state = homeViewModel.UiState.collectAsState()
    val savedArticles = homeViewModel.getSavedArticles.collectAsState().value


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),

        ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Top
        ) {
            Text(
                text = "Top Stories",
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Left,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 18.sp
            )
        }
        Spacer(Modifier.height(16.dp))

        PullToRefreshBox(
            isRefreshing = isRefreshingByPullDown,
            state= pullToRefreshState,
            onRefresh = {
                coroutineScope.launch {
                    homeViewModel.loadTopHeadLines()
                }
            },
            indicator = {
                Indicator(
                    modifier = Modifier.align(Alignment.TopCenter),
                    state = pullToRefreshState,
                    isRefreshing = isRefreshingByPullDown,
                    containerColor = MaterialTheme.colorScheme.background,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        ) {

                    when {
                        state.value.isLoading -> {
                            CircularProgressIndicator(
                                color = MaterialTheme.colorScheme.primary,
                            )
                        }

                        state.value.error != null -> {
                            Text(text = state.value.error.toString())
                        }

                        else -> {
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                userScrollEnabled = true,
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                            val sorted : List<TopHeadLinesModel.Article> = state.value.articles.sortedByDescending { toDate(it.publishedAt) }
                            items(sorted) {
                                val isSavedArticle = savedArticles.any { art -> art.url == it.url }
                                Card(
                                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                                    elevation = CardDefaults.cardElevation(8.dp),
                                    modifier = Modifier.clickable {
                                        homeViewModel.updateSelectedArticle(it)
                                        navController.navigate(NavItems.Article.route)
                                    }) {
                                    AsyncImage(
                                        model = ImageRequest.Builder(context).data(it.image).crossfade(true).build(),
                                        contentDescription = null,
                                        modifier = Modifier.aspectRatio(16f / 9f)
                                    )
                                    Row(
                                        modifier = Modifier.padding(
                                            horizontal = 8.dp, vertical = 8.dp
                                        )
                                    ) {
                                        Text(
                                            text = it.title,
                                            modifier = Modifier.weight(0.8f),
                                            style = TextStyle(
                                                fontSize = 18.sp,
                                                lineHeight = 18.sp,
                                                textAlign = TextAlign.Left,
                                                fontWeight = FontWeight.W400,
                                                color = MaterialTheme.colorScheme.onBackground,
                                            )
                                        )
                                        IconButton(
                                            colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.primary),
                                            onClick = {
                                                if (isSavedArticle) {
                                                    Toast.makeText(
                                                        context,
                                                        "Article Already saved ",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                } else {
                                                    coroutineScope.launch {
                                                        homeViewModel.saveArticle(it.url)
                                                    }
                                                }
                                            },
                                            modifier = Modifier
                                                .padding(8.dp)
                                                .weight(0.2f)
                                                .align(alignment = Alignment.CenterVertically)
                                                .size(24.dp)
                                        ) {
                                            Icon(
                                                modifier = Modifier.align(Alignment.CenterVertically),
                                                imageVector = if (isSavedArticle) Icons.Filled.Bookmark else Icons.Default.BookmarkBorder,
                                                contentDescription = "add to favourites",
                                                tint = MaterialTheme.colorScheme.primary
                                            )
                                        }
                                    }
                                    Text(
                                        text = "${timeAgoFromISO(it.publishedAt)} • ${it.source}",
                                        modifier = Modifier.padding(8.dp),
                                        style = TextStyle(
                                            fontSize = 14.sp,
                                            lineHeight = 14.sp,
                                            fontWeight = FontWeight.W300,
                                            textAlign = TextAlign.Left,
                                            color = MaterialTheme.colorScheme.onTertiary
                                        )
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                    }
                }
            }
        }


    }

