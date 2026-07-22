package com.arjuna.inbrief.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.arjuna.inbrief.ui.navigation.NavItems
import com.arjuna.inbrief.ui.utils.timeAgoFromISO
import com.arjuna.inbrief.ui.viewModel.HomeScreenViewModel
import com.arjuna.inbrief.ui.viewModel.SavedScreenViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SavedScreen(
    navController: NavController,
    savedScreenViewModel: SavedScreenViewModel = hiltViewModel(),
    homeScreenViewModel: HomeScreenViewModel = hiltViewModel()
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = "Saved Stories",
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Left,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 18.sp
                )
            }
            Spacer(Modifier.height(16.dp))
            val articles = savedScreenViewModel.getSavedArticles.collectAsState()
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                userScrollEnabled = true,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                item {

                    when {
                        articles.value.isEmpty() -> {

                            Text("No Saved Articles")

                        }

                        else -> {
                            articles.value.forEach {
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
                                            colors = IconButtonDefaults.iconButtonColors(
                                                contentColor = MaterialTheme.colorScheme.primary
                                            ),
                                            onClick = {
                                                CoroutineScope(Dispatchers.IO).launch {
                                                    savedScreenViewModel.deleteArticle(it.url)
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
                                                imageVector = Icons.Default.Delete,
                                                contentDescription = "add to favourites",
                                                tint = Color.Red
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
}