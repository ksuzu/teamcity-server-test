package pageModel

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

class UsersModule(private val driver: WebDriver) {
    var userCounter: WebElement
    var createUserButton: WebElement

    val userCreationPage: UserCreationPage
        get() {
            createUserButton.click()
            return UserCreationPage(driver)
        }

    val usersCount: Long?
        get() = java.lang.Long.valueOf(userCounter.text)

    init {
        this.createUserButton = driver.findElement(USER_TABLE).findElement(By.partialLinkText("Create user account"))
        this.userCounter = driver.findElement(USER_TABLE).findElement(By.className("usersActions")).findElement(By.className("pagerDesc")).findElement(By.tagName("strong"))
    }

    companion object {
        private val USER_TABLE = By.id("userTableInner")
    }
}
