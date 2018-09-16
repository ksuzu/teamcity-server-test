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

        Assert.assertEquals(true, UserPanel(driver).isAdministrationPageLinkAbset)
    }
}