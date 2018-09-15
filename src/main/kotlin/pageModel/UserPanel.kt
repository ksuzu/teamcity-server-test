package pageModel

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

class UserPanel(private val driver: WebDriver) {
    var userPanel: WebElement

    val isAdministrationPageLinkAbset: Boolean?
        get() = userPanel.findElements(By.partialLinkText("Administration")).isEmpty()

    init {
        userPanel = driver.findElement(USER_PANEL)
    }

    fun administrationPageLink(): WebElement {
        //FIXME искать по тексту не очень
        return userPanel.findElement(By.partialLinkText("Administration"))
    }

    fun openAdministrationPage(): AdministrationPage {
        userPanel.findElement(By.partialLinkText("Administration")).click()
        return AdministrationPage(driver)
    }

    companion object {
        private val USER_PANEL = By.id("userPanel")
    }
}
