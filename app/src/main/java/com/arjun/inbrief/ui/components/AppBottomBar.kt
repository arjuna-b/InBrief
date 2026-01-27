package com.arjun.inbrief.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.unit.dp
import com.arjun.inbrief.ui.navigation.NavItems

@Composable
fun AppBottomBar(items: List<NavItems>, currentRoute: String?, onItemClick: (NavItems) -> Unit) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.background,
        modifier = Modifier.height(64.dp),

//        modifier = Modifier.background(MaterialTheme.colorScheme.background)
    ) {
        items.forEach {
            NavigationBarItem(
                selected = currentRoute == it.route,
                label = { Text(it.name) },
                icon = { Icon(imageVector = it.icon, contentDescription = it.name) },
                onClick = { onItemClick(it) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}