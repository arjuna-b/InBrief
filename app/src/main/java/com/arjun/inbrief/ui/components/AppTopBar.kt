package com.arjun.inbrief.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String,
    subTitle: String,
    showBackIcon: Boolean,
    onClickBackIcon: (() -> Unit)? = null, // click event for backicon if we are passing function it will run that otherwise null
    actions: @Composable RowScope.() -> Unit = {} //HOF accepts composable code with {} nothing default
) {
    TopAppBar(
        modifier = Modifier.height(72.dp),
        title = {
            Text(
                text = title,
                fontWeight = FontWeight.ExtraBold,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
            )
//            Column(verticalArrangement = Arrangement.Center) {
//                Text(
//                    text = title,
//                    style = MaterialTheme.typography.titleLarge,
//                    color = MaterialTheme.colorScheme.primary,
//                )
//                subTitle?.let {
//                    Text(
//                        text = subTitle,
//                        style = MaterialTheme.typography.bodySmall,
//                        color = MaterialTheme.colorScheme.onBackground
//                    )
//                }
//            }
        },
        navigationIcon = {
            if (showBackIcon && onClickBackIcon != null) {
                IconButton(
                    onClick = onClickBackIcon
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "back button",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        },
        actions = actions,
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background),
    )
}