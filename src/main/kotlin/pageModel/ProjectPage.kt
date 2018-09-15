package pageModel

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

class ProjectPage(private val driver: WebDriver) {

    fun navigateToBuildTypeByName(buildTypeName: String): BuildTypePage {
        driver.findElement(By.linkText(buildTypeName)).click()
        return BuildTypePage(driver)
    }
}
