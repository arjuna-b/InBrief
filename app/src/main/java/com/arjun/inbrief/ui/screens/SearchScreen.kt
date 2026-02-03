package com.arjun.inbrief.ui.screens

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.arjun.inbrief.R
import com.arjun.inbrief.ui.navigation.NavItems
import com.arjun.inbrief.ui.utils.timeAgoFromISO
import com.arjun.inbrief.ui.viewModel.HomeScreenViewModel
import com.arjun.inbrief.ui.viewModel.SearchViewModel
import kotlinx.coroutines.coroutineScope

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchViewModel = hiltViewModel(),
    homeScreenViewModel: HomeScreenViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val searchResult = viewModel.result.collectAsState().value
    val focusManager = LocalFocusManager.current

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
        Column(modifier = Modifier.padding(16.dp)) {
            OutlinedTextField(
                value = viewModel.searchInput.value,
                onValueChange = { newValue ->
                    viewModel.searchInput.value = newValue
                    viewModel.onTextChanged()
                },
                maxLines = 1,
                trailingIcon = {
                    if (viewModel.searchInput.value.isNotEmpty()) {
                        IconButton(
                            onClick = {
                                viewModel.searchInput.value = ""
                                focusManager.clearFocus()
//                                Toast.makeText(context, "Cleared", Toast.LENGTH_SHORT).show()
                            }
                        ) {
                            Icon(imageVector = Icons.Default.Cancel, contentDescription = null)
                        }
                    }
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = {
//                    Toast.makeText(context, viewModel.searchInput.value, Toast.LENGTH_SHORT).show()
                    focusManager.clearFocus()
                    viewModel.onTextChanged()
                }),
                placeholder = { Text("Search Anything...") },
                shape = RoundedCornerShape(50),
                colors = TextFieldDefaults.colors(
                    focusedPlaceholderColor = MaterialTheme.colorScheme.onTertiary,
                    unfocusedPlaceholderColor = MaterialTheme.colorScheme.onTertiary,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        shape = RoundedCornerShape(50),
                        width = 1.dp,
                        color = Color.Unspecified
                    ),
            )
            Spacer(modifier = Modifier.height(22.dp))
            Text(
                text = "Search results :", style = TextStyle(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Left,
                    lineHeight = 18.sp
                )
            )
            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    userScrollEnabled = true,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    item {
                        when {
                            viewModel.searchInput.value.isEmpty() -> {

                                Image(
                                    painter = painterResource(R.drawable.search),
                                    contentDescription = null,
                                    modifier = Modifier.size(52.dp)
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    text = "Please enter something",
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        lineHeight = 14.sp,
                                        fontWeight = FontWeight.W300,
                                        textAlign = TextAlign.Left,
                                        color = MaterialTheme.colorScheme.onBackground
                                    )
                                )
                            }

                            viewModel.searchInput.value.length < 4 -> {
                                Image(
                                    painter = painterResource(R.drawable.search),
                                    contentDescription = null,
                                    modifier = Modifier.size(64.dp)
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    text = "Search Input should have more than 4 characters",
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        lineHeight = 14.sp,
                                        fontWeight = FontWeight.W300,
                                        textAlign = TextAlign.Left,
                                        color = MaterialTheme.colorScheme.onBackground
                                    )
                                )
                            }

                            searchResult.isLoading -> {

                                CircularProgressIndicator(
                                    color = MaterialTheme.colorScheme.primary,
                                )

                            }

                            searchResult.isError != null -> {
                                Text(text = searchResult.isError.toString())
                            }

                            else -> {
                                searchResult.data.forEach {
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
    }
}