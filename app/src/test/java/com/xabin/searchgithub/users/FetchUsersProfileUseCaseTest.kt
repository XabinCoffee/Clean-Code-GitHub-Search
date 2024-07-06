package com.xabin.searchgithub.users

import com.xabin.searchgithub.helper.EndpointTd
import com.xabin.searchgithub.networking.GitHubApi
import com.xabin.searchgithub.networking.users.UserSearchSchema
import com.xabin.searchgithub.testdata.UsersTestData
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Response


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
            Assert.assertEquals(UsersTestData.getUserProfile(), userProfile)
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

    // helper functions

    fun success() {
        // no-op
    }

    fun failure() {
        mEndpointTd.mFailure = true
    }

}