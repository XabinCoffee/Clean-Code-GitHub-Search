package com.xabin.searchgithub.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.tooling.preview.Preview
import com.xabin.searchgithub.screens.common.components.ImageAndTitleItem
import com.xabin.searchgithub.screens.theme.SearchGitHubTheme
import com.xabin.searchgithub.users.User

@Composable
fun ScrollableFollowerRow(
    items: List<User>,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(all = 16.dp)
    ) {
        items(items) {item ->
            ImageAndTitleItem(
                imageUrl = item.avatarUrl,
                login = item.username
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 400)
@Composable
fun ScrollableFollowerRowPreview() {
    val mockUsers = listOf(
        User(1,"username1", "user 1", "url1", "avatarurl1"),
        User(2,"username2", "user 2", "url2", "avatarurl1")
    )
    SearchGitHubTheme { ScrollableFollowerRow(mockUsers) }
}