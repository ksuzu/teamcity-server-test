package tests.api

import dto.CreateBuildRequest
import org.junit.Test

import org.junit.Assert.assertEquals
import BaseApiTest
import org.apache.http.HttpStatus

class BuildQueueTests : BaseApiTest() {
    @Test
    fun getBuildQueue_shouldResponseOkStatus() {
        teamcityClientByDevUser.getBuildQueue()
    }

    @Test
    fun deleteTargetBuildFromBuildQueue_whenHasManyBuilds_shouldCancaledOnlyTarget() {
        teamcityClientForDataPrepare.getBuildQueueSize()
        //prepere test data
        val uniqBuildType = teamcityClientForDataPrepare.createUniqueBuildType()
        teamcityClientForDataPrepare.queueBuild(CreateBuildRequest(uniqBuildType.id))
        val uniqBuildType2 = teamcityClientForDataPrepare.createUniqueBuildType()
        val buildInQueue2 = teamcityClientForDataPrepare.queueBuild(CreateBuildRequest(uniqBuildType2.id))
        val buildNumberBefore = teamcityClientForDataPrepare.getBuildQueueSize()

        //test
        teamcityClientByAdminUser.removeBuildFromQueue(buildInQueue2, HttpStatus.SC_NO_CONTENT)
        val buildNumberAfter = teamcityClientForDataPrepare.getBuildQueueSize()
        val expectedBuildNumberAfter = buildNumberBefore - 1
        assertEquals("The number of builds in buildQueue after removing from the queue was different of expected!",
                expectedBuildNumberAfter, buildNumberAfter)
    }
}