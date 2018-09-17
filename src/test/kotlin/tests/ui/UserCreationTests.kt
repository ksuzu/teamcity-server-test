package tests.ui

import org.junit.Assert
import org.junit.Test
import pageModel.LoginPage
import pageModel.UserPanel
import BaseUITest
import java.util.*

class UserCreationTests : BaseUITest() {
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

        Assert.assertEquals("Expected error message was not displayed!","Passwords mismatch", userCreationPage.tryLookForPasswordError())

        val userModuleAfterAddingUser =  UserPanel(driver).openAdministrationPage().openUsersModule()
        Assert.assertEquals("Number of users after additon new user was different than expected!", userCountBefore, userModuleAfterAddingUser.getUsersCount())
    }
}