package tests.ui

import org.junit.Assert
import org.junit.Test
import pageModel.LoginPage
import BaseUITest

class BuildQueueTests : BaseUITest() {

    @Test
    fun runBuildWithParameters() {
        val buildType = teamcityClientForDataPrepare.createUniqueBuildType()

        val overviewPage = LoginPage(driver, TEAMCITY_BASE_URI_FOR_DRIVER).open().loginAs(adminUsername, adminPassword)
        val dialog = overviewPage
                .navigateToProjectByName(buildType.projectName)
                .navigateToBuildTypeByName(buildType.name)
                .navigateToCustomBuildDialog()

        dialog.navigateToParameters().addNewConfigurationParameter("name", "value")
        dialog.submitRunBuild()

        val numberOfBuildsOfThisTypeInQueue = teamcityClientForDataPrepare.getBuildQueue().build.count { build -> build.buildTypeId == buildType.id }
        Assert.assertEquals("Number of builds of this buildType in queue was different than expected!",
                1, numberOfBuildsOfThisTypeInQueue)
    }
}