package pageModel

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

class UsersModule(private val driver: WebDriver) {
    private val USER_TABLE = By.id("userTableInner")

    fun getUserCreationPage(): UserCreationPage {
        val createUserButton = driver.findElement(USER_TABLE).findElement(By.partialLinkText("Create user account"))
        createUserButton.click()
        return UserCreationPage(driver)
    }

    fun getUsersCount(): Long? {
        val userCounter = driver.findElement(USER_TABLE).findElement(By.className("usersActions")).findElement(By.className("pagerDesc")).findElement(By.tagName("strong"))
        return java.lang.Long.valueOf(userCounter.text)
    }
}
