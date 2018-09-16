package tests.api

import BaseApiTest
import org.apache.http.HttpStatus
import org.junit.Test

class ProjectTests : BaseApiTest() {

    @Test
    fun createProjectByDevUser_ShouldBeResponseForbidden() {
        teamcityClientByDevUser.createUniqueProject(HttpStatus.SC_FORBIDDEN)
    }

    @Test
    fun createProjectByAdminUser_ShouldBeResponseOk() {
        teamcityClientByAdminUser.createUniqueProject(HttpStatus.SC_OK)
    }
}