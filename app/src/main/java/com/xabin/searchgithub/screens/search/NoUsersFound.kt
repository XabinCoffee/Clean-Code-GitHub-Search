package com.xabin.searchgithub.screens.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xabin.searchgithub.R

@Preview(showBackground = true)
@Composable
fun NoUsersFound() {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(16.dp)) {
        Image(
            painter = painterResource(id = R.drawable.octocat),
            contentDescription = null,
            modifier = Modifier
                .size(128.dp)
                .padding(12.dp)
        )
        Text(text = stringResource(id = R.string.no_users_found), style = MaterialTheme.typography.titleMedium)
    }
}