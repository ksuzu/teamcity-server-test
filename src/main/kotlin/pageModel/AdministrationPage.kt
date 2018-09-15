package pageModel

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

class AdministrationPage(private val driver: WebDriver) {

    fun openUsersModule(): UsersModule {
        driver.findElement(ADMIN_CONTEINER).findElement(ADMIN_SIDEBAR).findElement(By.partialLinkText("Users")).click()
        return UsersModule(driver)
    }

    companion object {
        private val ADMIN_CONTEINER = By.id("admin-container")
        private val ADMIN_SIDEBAR = By.className("admin-sidebar")
    }
}
