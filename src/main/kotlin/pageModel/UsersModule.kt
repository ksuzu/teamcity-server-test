package pageModel

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

class UsersModule(private val driver: WebDriver) {
    private val userCounter: WebElement
    private val createUserButton: WebElement

    init {
        this.createUserButton = driver.findElement(USER_TABLE).findElement(By.partialLinkText("Create user account"))
        this.userCounter = driver.findElement(USER_TABLE).findElement(By.className("usersActions")).findElement(By.className("pagerDesc")).findElement(By.tagName("strong"))
    }

    fun getUserCreationPage(): UserCreationPage {
        createUserButton.click()
        return UserCreationPage(driver)
    }

    fun getUsersCount(): Long? = java.lang.Long.valueOf(userCounter.text)

    companion object {
        private val USER_TABLE = By.id("userTableInner")
    }
}
