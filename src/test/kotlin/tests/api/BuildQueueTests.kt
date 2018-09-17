package tests.api

import dto.CreateBuildRequest
import org.junit.Test

import org.junit.Assert.assertEquals
import BaseApiTest
import org.apache.http.HttpStatus
import java.util.concurrent.TimeUnit

class BuildQueueTests : BaseApiTest() {
    @Test
    fun getBuildQueue_shouldResponseOkStatus() {
        teamcityClientByDevUser.getBuildQueue()
    }

    @Test
    fun deleteTargetBuildFromBuildQueue_whenHasManyBuilds_shouldCancaledOnlyTarget() {
        //prepere test data
        val uniqBuildType = teamcityClientForDataPrepare.createUniqueBuildType()
        teamcityClientForDataPrepare.queueBuild(CreateBuildRequest(uniqBuildType.id))
        val uniqBuildType2 = teamcityClientForDataPrepare.createUniqueBuildType()
        val buildInQueue2 = teamcityClientForDataPrepare.queueBuild(CreateBuildRequest(uniqBuildType2.id))
        val buildNumberBefore = teamcityClientForDataPrepare.getBuildQueueSize()

        //test
        teamcityClientByAdminUser.removeBuildFromQueue(buildInQueue2, HttpStatus.SC_OK)
        val buildNumberAfter = teamcityClientForDataPrepare.getBuildQueueSize()
        val expectedBuildNumberAfter = buildNumberBefore - 1
        assertEquals("The number of builds in buildQueue after removing from the queue was different than expected!",
                expectedBuildNumberAfter, buildNumberAfter)
    }

    @Test
    fun repeatingQueueBuild_whenQuietPeriodWasEnd_shouldAddSecondBuildIntoQueue() {
        val defaultQuietPeriodFromSeconds = 60L
        val uniqBuildType = teamcityClientForDataPrepare.createUniqueBuildType()

        teamcityClientByDevUser.queueBuild(CreateBuildRequest(uniqBuildType.id))
        TimeUnit.SECONDS.sleep(defaultQuietPeriodFromSeconds + 1)

        val buildNumberBefore = teamcityClientForDataPrepare.getBuildQueueSize()
        teamcityClientByDevUser.queueBuild(CreateBuildRequest(uniqBuildType.id))
        val buildNumberAfter = teamcityClientForDataPrepare.getBuildQueueSize()


        val expectedBuildNumberAfter = buildNumberBefore + 1
        assertEquals("The number of builds in buildQueue was different than  expected!",
                expectedBuildNumberAfter, buildNumberAfter)
    }

    @Test
    fun repeatingQueueBuild_whenQuietPeriodWasNotEnd_shouldNotAddSecondBuildIntoQueue() {
        val uniqBuildType = teamcityClientForDataPrepare.createUniqueBuildType()
        teamcityClientByDevUser.queueBuild(CreateBuildRequest(uniqBuildType.id))


        val buildNumberBefore = teamcityClientForDataPrepare.getBuildQueueSize()
        teamcityClientByDevUser.queueBuild(CreateBuildRequest(uniqBuildType.id))
        val buildNumberAfter = teamcityClientForDataPrepare.getBuildQueueSize()

        assertEquals("The number of builds in buildQueue was different than  expected!",
                buildNumberBefore, buildNumberAfter)
    }
}