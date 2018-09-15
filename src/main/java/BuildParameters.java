import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BuildParameters {
    private WebDriver driver;
    private static final By NEW_PARAMETER_NAME = By.id("customBuild-parameterName");
    private static final By NEW_PARAMETER_VALUE = By.id("customBuild-parameterValue");
    private static final By ADD_PARAMETER_BUTTON = By.xpath("//input[@value='add']");

    public BuildParameters(WebDriver driver) {
        this.driver = driver;
    }

    public void addNewConfigurationParameter(String name, String value){
        fillParameterName(name);
        fillParameterValue(value);
        submitAddingParameter();
    }

    private void fillParameterName(String name){
        driver.findElement(NEW_PARAMETER_NAME).sendKeys(name);
    }

    private void fillParameterValue(String value){
        driver.findElement(NEW_PARAMETER_VALUE).sendKeys(value);
    }

    private void submitAddingParameter(){
        driver.findElement(ADD_PARAMETER_BUTTON).click();
    }
}
