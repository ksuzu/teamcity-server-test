import dto.CreateBuildRequest
import dto.BuildType
import org.apache.http.HttpStatus
import org.junit.Test

import org.junit.Assert.assertEquals

class DeleloperTests : BaseApiTest() {
    private val teamcityClientByTestUser = TeamcityClient("user1", "user1")

    @Test
    fun getBuildsTest() {
        teamcityClientByTestUser.getBuilds()
    }

    @Test
    fun createProject_ShouldBeForbidden() {
        teamcityClientByTestUser.createUniqueProject(HttpStatus.SC_FORBIDDEN)
    }

    @Test
    //TODO почему-то не работает авторизация через куки
    fun queueNewBuild() {
        //prepere test data
        val buildNumberBefore = teamcityClientForDataPrepare.getBuildQueueSize()
        val uniqBuildType = teamcityClientForDataPrepare.createUniqueBuildType()
        val buildRequest = createBuildRequest(uniqBuildType)

        //test
        val createdBuild = teamcityClientByTestUser.queueBuild(buildRequest)
        val buildNumberAfter = teamcityClientForDataPrepare.getBuildQueueSize()
        val expectedBuildNumberAfter = buildNumberBefore + 1
        assertEquals(expectedBuildNumberAfter, buildNumberAfter)
        assertEquals(buildRequest.buildTypeId, createdBuild.buildTypeId)
    }

    @Test
    fun createAndDeleteThisBuildFromBuildQueue() {
        //prepere test data
        val uniqBuildType = teamcityClientForDataPrepare.createUniqueBuildType()
        val buildRequest = createBuildRequest(uniqBuildType)
        val buildInQueue = teamcityClientByTestUser.queueBuild(buildRequest)
        val buildNumberBefore = teamcityClientForDataPrepare.getBuildQueueSize()

        //test
        teamcityClientByTestUser.removeBuildFromQueue(buildInQueue)
        val buildNumberAfter = teamcityClientForDataPrepare.getBuildQueueSize()
        val expectedBuildNumberAfter = buildNumberBefore - 1
        assertEquals("The number of builds in buildQueue after removing from the queue was different of expected!",
                expectedBuildNumberAfter, buildNumberAfter)
    }

    @Test
    fun deleteExistedBuildFromBuildQueue_ShouldBeSuccess() {
        //prepere test data
        val uniqBuildType = teamcityClientForDataPrepare.createUniqueBuildType()
        val buildRequest = createBuildRequest(uniqBuildType)
        val buildInQueue = teamcityClientForDataPrepare.queueBuild(buildRequest)
        val buildNumberBefore = teamcityClientForDataPrepare.getBuildQueueSize()
        //test
        teamcityClientByTestUser.removeBuildFromQueue(buildInQueue)
        val buildNumberAfter = teamcityClientForDataPrepare.getBuildQueueSize()
        val expectedBuildNumberAfter = buildNumberBefore - 1
        assertEquals("The number of builds in buildQueue after removing from the queue was different of expected!",
                expectedBuildNumberAfter, buildNumberAfter)
    }

    @Test
    fun deleteBuildCreatedAnotherUserFromBuildQueue_ShouldBeForbidden() {
        //prepere test data
        val uniqBuildType = teamcityClientForDataPrepare.createUniqueBuildType()
        val buildRequest = createBuildRequest(uniqBuildType)
        val buildInQueue = teamcityClientForDataPrepare.queueBuild(buildRequest)
        val buildNumberBefore = teamcityClientForDataPrepare.getBuildQueueSize()
        //test
        teamcityClientByTestUser.removeBuildFromQueue(buildInQueue, HttpStatus.SC_FORBIDDEN)
        val buildNumberAfter = teamcityClientForDataPrepare.getBuildQueueSize()
        assertEquals("The number of builds in the queue before and after cancel-operation is not equal!",
                buildNumberBefore, buildNumberAfter)
    }

    @Test
    fun getBuildTypesTest() {
        teamcityClientByTestUser.getBuildTypes()
    }

    private fun createBuildRequest(buildType: BuildType): CreateBuildRequest {
        return CreateBuildRequest(buildType.id)
    }
}
