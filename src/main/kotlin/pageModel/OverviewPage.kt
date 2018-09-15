package pageModel

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

class OverviewPage(private val driver: WebDriver) {

    fun navigateToProjectByName(projectName: String): ProjectPage {
        driver.findElement(By.linkText(projectName)).click()
        return ProjectPage(driver)
    }
}
