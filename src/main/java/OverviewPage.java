import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class OverviewPage {
    private WebDriver driver;

    public OverviewPage(WebDriver driver) {
        this.driver = driver;
    }

    public ProjectPage navigateToProjectByName(String projectName){
        driver.findElement(By.linkText(projectName)).click();
        return new ProjectPage(driver);
    }
}
