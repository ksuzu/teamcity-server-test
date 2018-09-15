import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BuildTypePage {
    private WebDriver driver;
    private static final By RUN_BUILD_DIALOG = By.id("runBuildDialog");

    public BuildTypePage(WebDriver driver) {
        this.driver = driver;
    }

    public void runDefaultBuild(){
        driver.findElement(By.className("btn-group_run")).findElement(By.linkText("Run")).click();
    }

    public CustomBuildDialog navigateToCustomBuildDialog(){
        driver.findElement(By.className("btn-group_run")).findElement(By.xpath("//button[(@title='Run custom build')]")).click();
        WebDriverWait wait = new WebDriverWait(driver, 5);
        WebElement customBuildDialogElement = wait.until(ExpectedConditions.presenceOfElementLocated(RUN_BUILD_DIALOG));
        return new CustomBuildDialog(driver, customBuildDialogElement);
    }
}
