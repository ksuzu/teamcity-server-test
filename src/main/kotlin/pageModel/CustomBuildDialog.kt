package pageModel

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

class CustomBuildDialog(private val driver: WebDriver, webElement: WebElement) {

    fun submitRunBuild(): BuildTypePage {
        driver.findElement(RUN_BUTTON).click()
        return BuildTypePage(driver)
    }

    fun navigateToParameters(): BuildParameters {
        driver.findElement(By.linkText("Parameters")).click()
        return BuildParameters(driver)
    }

    companion object {
        private val RUN_BUTTON = By.id("runCustomBuildButton")
    }
}
