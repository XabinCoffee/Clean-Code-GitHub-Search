package com.xabin.searchgithub.users

import com.xabin.searchgithub.helper.EndpointTd
import com.xabin.searchgithub.testdata.UsersTestData
import com.xabin.searchgithub.users.usecase.FetchUserFollowersUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class FetchUsersFollowersUseCaseTest {
    private val USERNAME = "username"

    private lateinit var SUT: FetchUserFollowersUseCase
    private lateinit var mEndpointTd: EndpointTd

    @Before
    fun setup() {
        mEndpointTd = EndpointTd()
        SUT = FetchUserFollowersUseCase(mEndpointTd)
    }


    @Test
    fun `getUserFollowers success returns list of users`(){
        runTest {
            // Arrange
            success()
            // Act
            val followers = SUT(USERNAME)
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
            val followers = SUT(USERNAME)
            // Assert
            Assert.assertEquals(emptyList<User>(), followers)
        }
    }

    fun success() {
        // no-op
    }

    fun failure() {
        mEndpointTd.mFailure = true
    }
}