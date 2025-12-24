package com.xabin.searchgithub.screens.profile.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.xabin.searchgithub.R
import com.xabin.searchgithub.screens.theme.SearchGitHubTheme

@Composable
fun UserCard(
    imageUrl: String,
    name: String,
    login: String,
    modifier: Modifier = Modifier,
    bio: String = "",
    publicRepos: Int,
    followers: Int,
    following: Int

) {
    Surface(
        color = MaterialTheme.colorScheme.secondaryContainer,
        shape = MaterialTheme.shapes.medium,
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(128.dp)
                    .clip(CircleShape))

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = name,
                style = MaterialTheme.typography.titleMedium)

            Text(text = login,
                style = MaterialTheme.typography.labelMedium)

            Spacer(modifier = Modifier.height(8.dp))

            if (bio != "") {
                Text(text = bio,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodySmall)
            }

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)) {
                Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = stringResource(id = R.string.public_repos), style = MaterialTheme.typography.titleSmall)
                    Text(text = "$publicRepos", style = MaterialTheme.typography.labelMedium)
                }
                Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = stringResource(id = R.string.followers), style = MaterialTheme.typography.titleSmall)
                    Text(text = "$followers", style = MaterialTheme.typography.labelMedium)

                }
                Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = stringResource(id = R.string.following), style = MaterialTheme.typography.titleSmall)
                    Text(text = "$following", style = MaterialTheme.typography.labelMedium)
                }

            }

        }
    }
}


@Preview(showBackground = true, backgroundColor = 0x000000)
@Composable
fun UserItemPreview() {
    SearchGitHubTheme { UserCard(imageUrl = "http://via.placeholder.com/400", name = "Xabin Rodriguez", login = "XabinCoffee", bio = "Hello I like filling little text fields", followers = 5, following = 7, publicRepos = 11) }
}
