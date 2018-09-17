package tests.ui

import org.junit.*
import pageModel.LoginPage
import pageModel.UserPanel
import BaseUITest

class AvailabilityElementsFromOverviewPageTests : BaseUITest() {

    @Test
    fun testAvailabilityAdministrationModuleByDevUser() {
        val loginPage = LoginPage(driver, TEAMCITY_BASE_URI_FOR_DRIVER)
        loginPage.open().loginAs(devUsername, devPassword)

        Assert.assertEquals("Link of navigation to AdministrationPage was unexpectedly available to the user!",
                true, UserPanel(driver).isAdministrationPageLinkAbset)
    }
}