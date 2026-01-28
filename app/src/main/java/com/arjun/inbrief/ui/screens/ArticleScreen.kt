package com.arjun.inbrief.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.arjun.inbrief.ui.utils.timeAgoFromISO
import com.arjun.inbrief.ui.viewModel.ArticleViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Article(navController: NavController,articleViewModel: ArticleViewModel = hiltViewModel()) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        val selectedArticle by articleViewModel.selectedArticle.collectAsState()

        LazyColumn(modifier = Modifier.padding(16.dp)) {
            item {
                AsyncImage(
                    model = selectedArticle?.image,
                    contentDescription = null,
                    modifier = Modifier.aspectRatio(16f / 9f)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    selectedArticle?.title?:"",
                    style = TextStyle(
                        fontSize = 22.sp,
                        lineHeight = 22.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Left,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    selectedArticle?.content?:"",
                    style = TextStyle(
                        fontSize = 18.sp,
                        lineHeight = 18.sp,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Justify,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Published :${timeAgoFromISO(selectedArticle.publishedAt)}",
                    modifier = Modifier.padding(8.dp),
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 14.sp,
                        fontWeight = FontWeight.W300,
                        textAlign = TextAlign.Left,
                        color = MaterialTheme.colorScheme.onTertiary
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Source : ${selectedArticle?.source?:""}",
                    modifier = Modifier.padding(8.dp),
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 14.sp,
                        fontWeight = FontWeight.W300,
                        textAlign = TextAlign.Left,
                        color = MaterialTheme.colorScheme.onTertiary
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "URL : ${selectedArticle?.url?:""}" , modifier = Modifier.padding(8.dp), style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 14.sp,
                    fontWeight = FontWeight.W300,
                    textAlign = TextAlign.Left,
                    color = MaterialTheme.colorScheme.onTertiary
                ))
            }
        }

    }
}