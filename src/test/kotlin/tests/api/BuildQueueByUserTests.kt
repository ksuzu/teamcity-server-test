package tests.api

import Settings
import TeamcityClient
import dto.CreateBuildRequest
import org.junit.Test

import org.junit.Assert.assertEquals
import BaseApiTest
import dto.UserCredentials
import org.apache.http.HttpStatus
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters

@RunWith(Parameterized::class)
class BuildQueueByUserTests(private val userCredentials: UserCredentials) : BaseApiTest() {
    companion object {
        @JvmStatic
        @Parameters
        fun data(): Array<UserCredentials> {
            return arrayOf(UserCredentials(Settings.teamcityServerDevUsername, Settings.teamcityServerDevPassword), UserCredentials(Settings.teamcityServerAdminUsername, Settings.teamcityServerAdminPassword))
        }
    }

    @Test
    //TODO почему-то не работает авторизация через куки
    fun queueNewBuild_shouldIncreaseQueueSize() {
        val teamcityClientByTestUser = TeamcityClient(userCredentials)

        //prepere test data
        val buildNumberBefore = teamcityClientForDataPrepare.getBuildQueueSize()
        val uniqBuildType = teamcityClientForDataPrepare.createUniqueBuildType()
        val buildRequest = CreateBuildRequest(uniqBuildType.id)

        //test
        val createdBuild = teamcityClientByTestUser.queueBuild(buildRequest)
        val buildNumberAfter = teamcityClientForDataPrepare.getBuildQueueSize()
        val expectedBuildNumberAfter = buildNumberBefore + 1
        assertEquals("The number of builds in buildQueue after adding build to the queue was different than  expected!",
                expectedBuildNumberAfter, buildNumberAfter)
        assertEquals(buildRequest.buildTypeId, createdBuild.buildTypeId)
    }

    @Test
    fun deleteExistedBuildFromBuildQueue_shouldIncreaseQueueSize() {
        val teamcityClientByTestUser = TeamcityClient(userCredentials)

        //prepere test data
        val uniqBuildType = teamcityClientForDataPrepare.createUniqueBuildType()
        val buildRequest = CreateBuildRequest(uniqBuildType.id)
        val buildInQueue = teamcityClientForDataPrepare.queueBuild(buildRequest)
        val buildNumberBefore = teamcityClientForDataPrepare.getBuildQueueSize()

        //test
        teamcityClientByTestUser.removeBuildFromQueue(buildInQueue, HttpStatus.SC_NO_CONTENT)
        val buildNumberAfter = teamcityClientForDataPrepare.getBuildQueueSize()
        val expectedBuildNumberAfter = buildNumberBefore - 1
        assertEquals("The number of builds in buildQueue after removing from the queue was different than  expected!",
                expectedBuildNumberAfter, buildNumberAfter)
    }
}