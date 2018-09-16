import io.restassured.RestAssured
import org.junit.*
import pageModel.LoginPage
import pageModel.UserPanel

import java.util.UUID

class SeleniumTests : BaseUITest() {
    companion object {
        @BeforeClass
        @JvmStatic
        fun initTeamcityApiClient() {
            RestAssured.baseURI = TEAMCITY_BASE_URI_FOR_LOCAL
            RestAssured.basePath = "/app/rest/"
        }
    }

    @Test
    fun testAvailabilityAdministrationModuleByDevUser() {
        val loginPage = LoginPage(driver, TEAMCITY_BASE_URI_FOR_DRIVER)
        loginPage.open().loginAs(devUsername, devPassword)

        Assert.assertEquals(true, UserPanel(driver).isAdministrationPageLinkAbset)
    }

    @Test
    fun testCreateNewUserByAdmin() {
        val testUser = UUID.randomUUID().toString()
        val testPassword = UUID.randomUUID().toString()
        LoginPage(driver, TEAMCITY_BASE_URI_FOR_DRIVER).open().loginAs(adminUsername, adminPassword)

        val administrationPage = UserPanel(driver).openAdministrationPage()
        val userModuleBeforeAddingUser = administrationPage.openUsersModule()
        val userCountBefore = userModuleBeforeAddingUser.getUsersCount()
        val userCreationPage = userModuleBeforeAddingUser.getUserCreationPage()

        userCreationPage
                .fillUserField(testUser)
                .fillPasswordField(testPassword)
                .fillRetypedPasswordField(testPassword)
                .clickSubmitButton()

        val userModuleAfterAddingUser = administrationPage.openUsersModule()
        Assert.assertEquals(userCountBefore!! + 1, userModuleAfterAddingUser.getUsersCount())
    }

    @Test
    fun incorrectEnterRetypePasswordOnUserCreation_shouldGiveError() {
        val testUser = UUID.randomUUID().toString()
        val testPassword = UUID.randomUUID().toString()
        LoginPage(driver, TEAMCITY_BASE_URI_FOR_DRIVER).open().loginAs(adminUsername, adminPassword)

        val administrationPage = UserPanel(driver).openAdministrationPage()
        val userModuleBeforeAddingUser = administrationPage.openUsersModule()
        val userCountBefore = userModuleBeforeAddingUser.getUsersCount()
        val userCreationPage = userModuleBeforeAddingUser.getUserCreationPage()

        userCreationPage.fillUserField(testUser)
                .fillPasswordField(testPassword)
                .fillRetypedPasswordField("incorrectPwd")
                .clickSubmitButton()

        Assert.assertEquals("Passwords mismatch", userCreationPage.tryLookForPasswordError())

        val userModuleAfterAddingUser =  UserPanel(driver).openAdministrationPage().openUsersModule()
        Assert.assertEquals(userCountBefore, userModuleAfterAddingUser.getUsersCount())
    }

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
        Assert.assertEquals("Number of builds of this buildType in queue was different of expected!", 1, numberOfBuildsOfThisTypeInQueue)
    }
}