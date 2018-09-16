package pageModel

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

class LoginPage(private val driver: WebDriver, private val loginPageUrl: String)//String.format("%s/login.html", baseUri);
{

    fun open(): LoginPage {
        driver.get(loginPageUrl)
        return this
    }

    fun loginAs(user: String, password: String): OverviewPage {
        driver.findElement(USERNAME_INPUT).sendKeys(user)
        driver.findElement(PASSWORD_INPUT).sendKeys(password)
        driver.findElement(LOG_IN_BUTTON).click()
        return OverviewPage(driver)
    }

    companion object {
        private val USERNAME_INPUT = By.id("username")
        private val PASSWORD_INPUT = By.id("password")
        private val LOG_IN_BUTTON = By.name("submitLogin")
    }
}
