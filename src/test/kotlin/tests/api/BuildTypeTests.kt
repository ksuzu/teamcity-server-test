package tests.api

import org.junit.Test
import BaseApiTest

class BuildTypeTests : BaseApiTest() {

    @Test
    fun getBuildTypesByDevUser_ShouldBeResponseOk() {
        teamcityClientByDevUser.getBuildTypes()
    }

    @Test
    fun getBuildTypesByAdminUser_ShouldBeResponseOk() {
        teamcityClientByAdminUser.getBuildTypes()
    }

}
