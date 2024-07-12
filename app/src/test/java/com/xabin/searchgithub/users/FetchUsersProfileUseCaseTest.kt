package com.xabin.searchgithub.users

import com.xabin.searchgithub.helper.EndpointTd
import com.xabin.searchgithub.networking.GitHubApi
import com.xabin.searchgithub.networking.users.UserSearchSchema
import com.xabin.searchgithub.testdata.UsersTestData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import kotlin.coroutines.EmptyCoroutineContext


class FetchUsersProfileUseCaseTest {

    private val USERNAME = "username"

    private lateinit var SUT: FetchUsersProfileUseCase
    private lateinit var mEndpointTd: EndpointTd

    @Before
    fun setup() {
        mEndpointTd = EndpointTd()
        SUT = FetchUsersProfileUseCase(mEndpointTd)
    }

    // User id sent to endpoint

    @Test
    fun `fetchUserProfile user id passed to endpoint`() {
        runTest {
            // Arrange
            // Act
            SUT.fetchUserProfile(USERNAME)
            // Assert
            Assert.assertEquals(1, mEndpointTd.mTimesCalled)
        }

    }

    @Test
    fun `fetchUserProfile success returns correct profile`() {
        runTest {
            // Arrange
            success()
            // Act
            val userProfile = SUT.fetchUserProfile(USERNAME)
            // Assert
            Assert.assertEquals(UsersTestData.getUser(), userProfile)
        }
    }

    @Test
    fun `fetchUserProfile failure returns null`() {
        runTest {
            // Arrange
            failure()
            // Act
            val userProfile = SUT.fetchUserProfile(USERNAME)
            // Assert
            Assert.assertNull(userProfile)

        }

    }

    @Test
    fun `getUserFollowers success returns list of users`(){
        runTest {
            // Arrange
            success()
            // Act
            val followers = SUT.fetchUserFollowers(USERNAME)
            // Assert
            Assert.assertEquals(UsersTestData.getUsers(), followers)

        }
    }

    @Test
    fun `getUserFollowers success returns empty list`(){
        runTest {
            // Arrange
            failure()
            // Act
            val followers = SUT.fetchUserFollowers(USERNAME)
            // Assert
            Assert.assertEquals(emptyList<User>(), followers)
        }
    }

    @Test
    fun `getUserRepos success returns list of repos`(){
        runTest {
            // Arrange
            success()
            // Act
            val repos = SUT.fetchUserRepos(USERNAME)
            // Assert
            Assert.assertEquals(UsersTestData.getRepositories(), repos)

        }
    }

    // helper functions

    fun success() {
        // no-op
    }

    fun failure() {
        mEndpointTd.mFailure = true
    }

}