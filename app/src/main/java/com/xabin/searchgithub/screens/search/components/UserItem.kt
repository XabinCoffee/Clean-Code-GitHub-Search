package com.xabin.searchgithub.screens.search.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.xabin.searchgithub.screens.theme.SearchGitHubTheme


@Composable
fun UserItem(
    imageUrl: String,
    name: String,
    onUserSelected: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = MaterialTheme.colorScheme.secondaryContainer,
        shape = MaterialTheme.shapes.medium,
        onClick = onUserSelected,
        modifier = modifier
    ) {
        Column(modifier = modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(88.dp)
                    .clip(CircleShape))

            Text(text = name,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.paddingFromBaseline(top = 24.dp, bottom = 8.dp))
        }
    }
}


@Preview(showBackground = true, backgroundColor = 0x000000)
@Composable
fun UserItemPreview() {
    SearchGitHubTheme { UserItem("http://via.placeholder.com/400", "Jaime", {}) }
}
