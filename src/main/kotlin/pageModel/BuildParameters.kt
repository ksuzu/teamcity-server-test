package pageModel

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

class BuildParameters(private val driver: WebDriver) {

    fun addNewConfigurationParameter(name: String, value: String) {
        fillParameterName(name)
        fillParameterValue(value)
        submitAddingParameter()
    }

    private fun fillParameterName(name: String) {
        driver.findElement(NEW_PARAMETER_NAME).sendKeys(name)
    }

    private fun fillParameterValue(value: String) {
        driver.findElement(NEW_PARAMETER_VALUE).sendKeys(value)
    }

    private fun submitAddingParameter() {
        driver.findElement(ADD_PARAMETER_BUTTON).click()
    }

    companion object {
        private val NEW_PARAMETER_NAME = By.id("customBuild-parameterName")
        private val NEW_PARAMETER_VALUE = By.id("customBuild-parameterValue")
        private val ADD_PARAMETER_BUTTON = By.xpath("//input[@value='add']")
    }
}
