package com.xabin.searchgithub.users

import com.xabin.searchgithub.common.database.SearchQueryDao
import com.xabin.searchgithub.helper.EndpointTd
import com.xabin.searchgithub.networking.GitHubApi
import com.xabin.searchgithub.networking.users.UserSchema
import com.xabin.searchgithub.networking.users.UserSearchSchema
import com.xabin.searchgithub.testdata.UsersTestData
import io.mockk.awaits
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Response



class SearchUsersUseCaseTest {

    private val USERS = UsersTestData.getUsers()
    private val QUERY = "query"

    private lateinit var endpointTd: EndpointTd
    private val searchQueryDao = mockk<SearchQueryDao>()

    lateinit var SUT: SearchUsersUseCase

    lateinit var dispatcher: CoroutineDispatcher

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        endpointTd = EndpointTd()
        dispatcher = UnconfinedTestDispatcher()
        SUT = SearchUsersUseCase(endpointTd, searchQueryDao)
    }

    // query string sent to the endpoint
    // query string empty, no calls to endpoint
    @Test
    fun `searchUsers query sent to endpoint`() {
        runTest {
            // Arrange
            // Act
            SUT.searchUsers(QUERY)
            // Assert
            Assert.assertEquals(1, endpointTd.mTimesCalled)
        }
    }

    @Test
    fun `searchUsers no API calls when string empty`() {
        runTest {
            // Arrange
            // Act
            SUT.searchUsers("")
            // Assert
            Assert.assertEquals(0, endpointTd.mTimesCalled)
        }
    }

    @Test
    fun `searchUsers user list returned`() {
        runTest {
            // Arrange
            success()
            // Act
            val users = SUT.searchUsers(QUERY)
            // Assert
            Assert.assertEquals(USERS, users)
        }
    }

    @Test
    fun `searchUsers failure returns empty list`() {
        runTest {
            // Arrange
            failure()
            // Act
            val users = SUT.searchUsers(QUERY)
            // Assert
            Assert.assertEquals(emptyList<User>(), users)
        }
    }

    @Test
    fun `searchUsers success saves search query`() {
        runTest {
            // Arrange
            success()
            coEvery { searchQueryDao.upsert(any()) } just runs
            // Act
            SUT.searchUsers(QUERY)
            // Assert
            coVerify { searchQueryDao.upsert(any()) }
        }
    }


    // Helper methods ------------------------------------------------------------------------------------

    private fun success() {
        // no op
    }

    private fun failure() {
        endpointTd.mFailure = true
    }


}