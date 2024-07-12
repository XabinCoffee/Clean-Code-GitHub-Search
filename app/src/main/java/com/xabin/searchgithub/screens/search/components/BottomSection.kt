package com.xabin.searchgithub.screens.search.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
                    columns = GridCells.Adaptive(140.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = modifier.fillMaxWidth()
                ) {
                    items(
                        items = users,
                        key = { user -> user.id }
                    ) { user ->
                        UserItem(imageUrl = user.avatarUrl, name = user.username, modifier = modifier.padding(8.dp), onUserSelected = { onUserSelected(user.username) })
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

