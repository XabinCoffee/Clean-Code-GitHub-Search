package com.xabin.searchgithub.users

import com.xabin.searchgithub.common.database.SearchQueryDao
import com.xabin.searchgithub.helper.EndpointTd
import com.xabin.searchgithub.testdata.UsersTestData
import com.xabin.searchgithub.users.usecase.SearchUsersUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test



class SearchUsersUseCaseTest {

    private val USERS = UsersTestData.getUsers()
    private val QUERY = "query"

    private lateinit var endpointTd: EndpointTd
    private val searchQueryDao = mockk<SearchQueryDao>(relaxUnitFun = true)

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
            SUT(QUERY)
            // Assert
            Assert.assertEquals(1, endpointTd.mTimesCalled)
        }
    }

    @Test
    fun `searchUsers no API calls when string empty`() {
        runTest {
            // Arrange
            // Act
            SUT("")
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
            val users = SUT(QUERY)
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
            val users = SUT(QUERY)
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
            SUT(QUERY)
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