package pageModel

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

class BuildTypePage(private val driver: WebDriver) {

    fun runDefaultBuild() {
        driver.findElement(By.className("btn-group_run")).findElement(By.linkText("Run")).click()
    }

    fun navigateToCustomBuildDialog(): CustomBuildDialog {
        driver.findElement(By.className("btn-group_run")).findElement(By.xpath("//button[(@title='Run custom build')]")).click()
        val wait = WebDriverWait(driver, 5)
        val customBuildDialogElement = wait.until(ExpectedConditions.presenceOfElementLocated(RUN_BUILD_DIALOG))
        return CustomBuildDialog(driver, customBuildDialogElement)
    }

    companion object {
        private val RUN_BUILD_DIALOG = By.id("runBuildDialog")
    }
}
