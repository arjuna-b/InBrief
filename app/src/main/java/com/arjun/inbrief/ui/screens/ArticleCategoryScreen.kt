package com.arjun.inbrief.ui.screens

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.arjun.inbrief.ui.navigation.NavItems
import com.arjun.inbrief.ui.utils.timeAgoFromISO
import com.arjun.inbrief.ui.viewModel.ArticleCategoryScreenViewModel
import com.arjun.inbrief.ui.viewModel.HomeScreenViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ArticleCategoryScreen(
    navController: NavController,
    articleCategoryScreenViewModel: ArticleCategoryScreenViewModel = hiltViewModel(),
    homeScreenViewModel: HomeScreenViewModel = hiltViewModel()
) {

    val news = articleCategoryScreenViewModel.data.collectAsState()


    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),

        ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Top
        ) {
            Text(
                text = "Top ${articleCategoryScreenViewModel.selectedCategory.value} Stories",
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Left,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 18.sp
            )
        }
        Spacer(Modifier.height(16.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            userScrollEnabled = true,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            item {

                when {
                    news.value.isLoading -> {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.primary,
                        )
                    }

                    news.value.isError != null -> {
                        Text(text = news.value.isError.toString())
                    }

                    else -> {
                        news.value.data.forEach {
                            Log.d("TAG", it.title)
                            Card(
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                                elevation = CardDefaults.cardElevation(8.dp),
                                modifier = Modifier.clickable {
                                    homeScreenViewModel.updateSelectedArticle(it)
                                    navController.navigate(NavItems.Article.route)
                                }
                            ) {
                                AsyncImage(
                                    model = it.image,
                                    contentDescription = null,
                                    modifier = Modifier.aspectRatio(16f / 9f)
                                )
                                Row(
                                    modifier = Modifier.padding(
                                        horizontal = 8.dp,
                                        vertical = 8.dp
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
                                            Toast.makeText(
                                                context,
                                                "button clicked",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        },
                                        modifier = Modifier
                                            .padding(8.dp)
                                            .weight(0.2f)
                                            .align(alignment = Alignment.CenterVertically)
                                            .size(24.dp)
                                    ) {
                                        Icon(
                                            modifier = Modifier.align(Alignment.CenterVertically),
                                            imageVector = Icons.Default.BookmarkBorder,
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