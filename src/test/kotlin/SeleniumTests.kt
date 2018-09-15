import io.restassured.RestAssured
import org.junit.*
import pageModel.LoginPage
import pageModel.UserPanel

import java.util.UUID

@Ignore
class SeleniumTests : BaseWebTest() {
    private val adminUsername = "admin"
    private val adminPassword = "admin1"
    private val devUsername = "user1"
    private val devPassword = "user1"
    private val teamcityClientForDataPrepare = TeamcityClient("admin", "admin1")

    companion object {
        @BeforeClass
        @JvmStatic
        fun initTeamcityApiClient() {
            RestAssured.baseURI = BASE_URI
            RestAssured.basePath = "/app/rest/"
        }
    }

    @Test
    fun testAvailabilityAdministrationModuleByDevUser() {
        val loginPage = LoginPage(driver, BASE_URI)
        loginPage.open()
        loginPage.loginAs(devUsername, devPassword)
        Assert.assertEquals(true, UserPanel(driver).isAdministrationPageLinkAbset)
    }

    @Test
    fun testCreateNewUserByAdmin() {
        val testUser = UUID.randomUUID().toString()
        val testPassword = UUID.randomUUID().toString()
        val loginPage = LoginPage(driver, BASE_URI)
        loginPage.open()
        loginPage.loginAs(adminUsername, adminPassword)
        val administrationPage = UserPanel(driver).openAdministrationPage()
        val userModuleBeforeAddingUser = administrationPage.openUsersModule()
        val userCountBefore = userModuleBeforeAddingUser.usersCount
        val userCreationPage = userModuleBeforeAddingUser.userCreationPage
        userCreationPage.fillUserField(testUser)
        userCreationPage.fillPasswordField(testPassword)
        userCreationPage.fillRetypedPasswordField(testPassword)
        userCreationPage.clickSubmitButton()

        val userModuleAfterAddingUser = administrationPage.openUsersModule()
        val expectedCount = userCountBefore!! + 1
        Assert.assertEquals(expectedCount, userModuleAfterAddingUser.usersCount)
    }

    @Test
    fun incorrectEnterRetypePasswordOnUserCreation_shouldGiveError() {
        val testUser = UUID.randomUUID().toString()
        val testPassword = UUID.randomUUID().toString()
        val loginPage = LoginPage(driver, BASE_URI)
        loginPage.open()
        loginPage.loginAs(adminUsername, adminPassword)
        val administrationPage = UserPanel(driver).openAdministrationPage()
        val userModuleBeforeAddingUser = administrationPage.openUsersModule()
        val userCountBefore = userModuleBeforeAddingUser.usersCount
        val userCreationPage = userModuleBeforeAddingUser.userCreationPage
        userCreationPage.fillUserField(testUser)
        userCreationPage.fillPasswordField(testPassword)
        userCreationPage.fillRetypedPasswordField("incorrectPwd")
        userCreationPage.clickSubmitButton()

        Assert.assertEquals("Passwords mismatch", userCreationPage.tryLookForPasswordError())

        UserPanel(driver).openAdministrationPage()
        val userModuleAfterAddingUser = administrationPage.openUsersModule()
        Assert.assertEquals(userCountBefore, userModuleAfterAddingUser.usersCount)
    }

    @Test
    fun runBuildWithParameters() {
        val (id, testBuildTypeName, testProjectName) = teamcityClientForDataPrepare.createUniqueBuildType()
        val loginPage = LoginPage(driver, BASE_URI)
        loginPage.open()
        val overviewPage = loginPage.loginAs(adminUsername, adminPassword)
        val dialog = overviewPage.navigateToProjectByName(testProjectName).navigateToBuildTypeByName(testBuildTypeName).navigateToCustomBuildDialog()
        dialog.navigateToParameters().addNewConfigurationParameter("name", "value")
        dialog.submitRunBuild()
        val d = teamcityClientForDataPrepare.getBuildQueue().build.stream().anyMatch { (_, buildTypeId) -> buildTypeId == id }
        Assert.assertEquals(true, d)
    }
}