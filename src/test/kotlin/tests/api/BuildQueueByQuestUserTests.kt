package tests.api

import TeamcityClient
import dto.CreateBuildRequest
import org.apache.http.HttpStatus
import org.junit.Test

import org.junit.Assert.assertEquals
import BaseApiTest
import dto.UserCredentials

class BuildQueueByQuestUserTests() : BaseApiTest() {

    @Test
    fun deleteBuildCreatedAnotherUserFromBuildQueue_ShouldBeForbidden() {
        val teamcityClientByTestUser = TeamcityClient(UserCredentials("guest", ""))

        //prepere test data
        val uniqBuildType = teamcityClientForDataPrepare.createUniqueBuildType()
        val buildRequest = CreateBuildRequest(uniqBuildType.id)
        val buildInQueue = teamcityClientForDataPrepare.queueBuild(buildRequest)
        val buildNumberBefore = teamcityClientForDataPrepare.getBuildQueueSize()

        //test
        teamcityClientByTestUser.removeBuildFromQueue(buildInQueue, HttpStatus.SC_FORBIDDEN)
        val buildNumberAfter = teamcityClientForDataPrepare.getBuildQueueSize()
        assertEquals("The number of builds in the queue before and after cancel-operation is not equal!",
                buildNumberBefore, buildNumberAfter)
    }


}