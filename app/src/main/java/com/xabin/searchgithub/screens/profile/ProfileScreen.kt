package com.xabin.searchgithub.screens.profile

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.xabin.searchgithub.R
import com.xabin.searchgithub.screens.ScreensNavigator
import com.xabin.searchgithub.screens.common.components.ElementSection
import com.xabin.searchgithub.screens.profile.components.RepoItem
import com.xabin.searchgithub.screens.profile.components.ScrollableFollowerRow
import com.xabin.searchgithub.screens.profile.components.UserCard
import androidx.core.net.toUri


@Composable
fun ProfileScreen(
    username: String,
    screensNavigator: ScreensNavigator,
    profileViewModel: ProfileViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(username) {
        profileViewModel.getUserProfile(username)
    }

    profileViewModel.errorMessage?.let {
        Toast.makeText(LocalContext.current, it, Toast.LENGTH_LONG).show()
        profileViewModel.resetErrorMessage()
        screensNavigator.navigateBack()
    }

    if (profileViewModel.detailsLoading) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(64.dp)
                    .padding(top = 16.dp),
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant)
        }
    } else if (profileViewModel.user != null) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)) {
            item {

                Spacer(modifier = Modifier.height(24.dp))

                UserCard(
                    imageUrl = profileViewModel.user!!.avatarUrl,
                    name = profileViewModel.user!!.name ?: "--",
                    login = profileViewModel.user!!.username,
                    bio = profileViewModel.user!!.bio,
                    followers = profileViewModel.user!!.followers,
                    following = profileViewModel.user!!.following,
                    publicRepos = profileViewModel.user!!.publicRepos,
                    modifier = Modifier.padding(top = 24.dp, start = 24.dp, end = 24.dp))

                Spacer(modifier = Modifier.height(8.dp))

                if (profileViewModel.followersLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(32.dp)
                            .padding(top = 16.dp),
                        color = MaterialTheme.colorScheme.secondary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant)
                } else {
                    ElementSection(
                        title = stringResource(id = R.string.followers),
                        itemCount = profileViewModel.user!!.followers,
                        content = {
                            ScrollableFollowerRow(items = profileViewModel.followers)
                        }
                    )
                }
            }
            if (profileViewModel.reposLoading) {
                item {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(32.dp)
                            .padding(top = 16.dp),
                        color = MaterialTheme.colorScheme.secondary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant)
                }
            } else {
                item {
                    ElementSection(title = stringResource(id = R.string.public_repos), itemCount = profileViewModel.user!!.publicRepos, modifier = Modifier.fillMaxWidth())
                }
                items(profileViewModel.repos) { repo ->
                    RepoItem(
                        name = repo.name,
                        description = repo.description,
                        language = repo.language,
                        onClick = { context.startActivity(Intent(Intent.ACTION_VIEW,
                            repo.htmlUrl.toUri())) },
                        modifier = Modifier.padding(horizontal = 18.dp)
                    )
                }
            }
            item {
                Spacer(modifier = Modifier.height(38.dp))
            }
        }
    }
}