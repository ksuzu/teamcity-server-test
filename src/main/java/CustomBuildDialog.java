import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CustomBuildDialog {
    private WebDriver driver;
    private static final By RUN_BUTTON = By.id("runCustomBuildButton");

    public CustomBuildDialog(WebDriver driver, WebElement webElement) {
        this.driver = driver;
    }

    public BuildTypePage submitRunBuild(){
        driver.findElement(RUN_BUTTON).click();
        return new BuildTypePage(driver);
    }

    public BuildParameters navigateToParameters(){
        driver.findElement(By.linkText("Parameters")).click();
        return new BuildParameters(driver);
    }
}
