package com.arjun.inbrief.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.arjun.inbrief.R
import com.arjun.inbrief.ui.navigation.NavItems
import com.arjun.inbrief.ui.viewModel.CategoriesViewModel

@Composable
fun CategoriesScreen(navController: NavController, categoriesViewModel: CategoriesViewModel = hiltViewModel()){
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopStart){
        val context = LocalContext.current
        val categories = listOf<categories>(
            categories("Business",R.drawable.business),
            categories("Entertainment",R.drawable.cinema),
            categories("Health",R.drawable.healthcare),
            categories("Sports",R.drawable.sports),
            categories("Science",R.drawable.science),
            categories("Technology",R.drawable.technology),
        )



        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(12.dp)
            ) {
            items(categories){
                OutlinedCard(
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    modifier = Modifier.clickable(true){
//                        Toast.makeText(context, it.name, Toast.LENGTH_SHORT).show()
                        categoriesViewModel.updateTitleInActionBar(it.name)
                        navController.navigate(NavItems.ArticleCategoryScreen.route)
                    }
                ) {
//                    Box(contentAlignment = Alignment.TopCenter, modifier = Modifier.padding(8.dp).aspectRatio(16f/9f)) {
//                        Image(painter = painterResource(id =  it.image), contentDescription = it.name)
//                        Spacer(modifier = Modifier.height(12.dp))
//                        Text(text = it.name, modifier = Modifier.align(Alignment.BottomCenter),style = TextStyle(
//                            fontSize = 18.sp,
//                            fontWeight = FontWeight.Bold,
//                            color = MaterialTheme.colorScheme.onBackground,
//                            lineHeight = 18.sp,
//                            textAlign = TextAlign.Center
//                        ))
//                    }
                    Column(
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(painter = painterResource(id =  it.image), contentDescription = it.name, modifier = Modifier
                            .aspectRatio(16f / 9f)
                            .padding(8.dp))
                        Text(text = it.name, style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground,
                            lineHeight = 18.sp,
                            textAlign = TextAlign.Center
                        ), modifier = Modifier.padding(8.dp))
                    }
                }
            }
        }
    }
}

data class categories(
    val name: String,
    val image: Int
)