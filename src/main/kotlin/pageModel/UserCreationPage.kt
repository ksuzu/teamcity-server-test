package pageModel

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

class UserCreationPage(private val driver: WebDriver) {

    fun fillUserField(teamcityUsername: String): UserCreationPage {
        driver.findElement(USERNAME).sendKeys(teamcityUsername)
        return this;
    }

    fun fillNameField(name: String) {
        driver.findElement(NAME).sendKeys(name)
    }

    fun fillPasswordField(password: String): UserCreationPage {
        driver.findElement(PASSWORD1).sendKeys(password)
        return this
    }

    fun fillRetypedPasswordField(password: String): UserCreationPage {
        driver.findElement(RETYPED_PASSWORD).sendKeys(password)
        return this
    }

    fun clickSubmitButton(): UserCreationPage {
        driver.findElement(SUBMIT_BUTTON).click()
        return this
    }

    fun tryLookForPasswordError(): String {
        return driver.findElement(By.className("input-wrapper_password1")).getAttribute("data-error")
    }

    companion object {
        private val USERNAME = By.id("input_teamcityUsername")
        private val NAME = By.id("name")
        private val PASSWORD1 = By.id("password1")
        private val RETYPED_PASSWORD = By.id("retypedPassword")
        private val SUBMIT_BUTTON = By.name("submitCreateUser")
        private val PASSWORD_ERROR = By.tagName("data-error")
    }
}
