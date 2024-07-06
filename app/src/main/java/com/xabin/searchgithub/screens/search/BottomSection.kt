package com.xabin.searchgithub.screens.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.xabin.searchgithub.R
import com.xabin.searchgithub.users.User

@Composable
fun BottomSection(
    showStartup: Boolean,
    loading: Boolean,
    users: List<User>,
    onUserSelected: (String) -> Unit,
    gridCount: Int,
    page: Int,
    onPageIncrease: () -> Unit,
    onPageDecrease: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (showStartup) {
        StartupMessage()
    } else {
        if (loading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .width(64.dp)
                    .padding(top = 16.dp),
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
        } else {
            if (users.isEmpty()) {
                NoUsersFound()
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(gridCount),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = modifier.fillMaxWidth()
                ) {
                    items(
                        items = users,
                        key = { user -> user.id }
                    ) { user ->
                        UserItem(imageUrl = user.avatarUrl, name = user.name ?: "", modifier = modifier.padding(8.dp), onUserSelected = { /* TODO */ })
                    }


                    /*item(
                        span = { GridItemSpan(gridCount) }
                    ) {
                        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                            Pagination(
                                page = page,
                                onPageIncrease = onPageIncrease,
                                onPageDecrease = onPageDecrease,
                            )
                        }
                    }*/
                }
            }
        }
    }

}

@Composable
fun StartupMessage() {
    Text(
        text = stringResource(id = R.string.startup_message),
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.padding(vertical = 32.dp, horizontal = 16.dp))
}

