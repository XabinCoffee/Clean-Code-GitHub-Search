package com.xabin.searchgithub.users

import com.xabin.searchgithub.helper.EndpointTd
import com.xabin.searchgithub.testdata.UsersTestData
import com.xabin.searchgithub.users.usecase.FetchUserProfileUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class FetchUsersProfileUseCaseTest {

    private val USERNAME = "username"

    private lateinit var SUT: FetchUserProfileUseCase
    private lateinit var mEndpointTd: EndpointTd

    @Before
    fun setup() {
        mEndpointTd = EndpointTd()
        SUT = FetchUserProfileUseCase(mEndpointTd)
    }

    // User id sent to endpoint

    @Test
    fun `fetchUserProfile user id passed to endpoint`() {
        runTest {
            // Arrange
            // Act
            SUT(USERNAME)
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
            val userProfile = SUT(USERNAME)
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
            val userProfile = SUT(USERNAME)
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