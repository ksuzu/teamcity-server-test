package tests.api

import org.junit.Test
import BaseApiTest

class BuildsTests : BaseApiTest() {

    @Test
    fun getBuildsByDevUser() {
        teamcityClientByDevUser.getBuilds()
    }

    @Test
    fun getBuildsByAdminUser() {
        teamcityClientByDevUser.getBuilds()
    }
}
