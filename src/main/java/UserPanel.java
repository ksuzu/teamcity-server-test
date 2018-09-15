import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class UserPanel {
    private WebDriver driver;
    private String mainPageUrl;
    private static final By USER_PANEL = By.id("userPanel");
    public WebElement userPanel;

    public UserPanel(WebDriver driver) {
        this.driver = driver;
        userPanel = driver.findElement(USER_PANEL);
    }

    public WebElement administrationPageLink() {
        //FIXME искать по тексту не очень
        return userPanel.findElement(By.partialLinkText("Administration"));
    }

    public Boolean isAdministrationPageLinkAbset() {
        return userPanel.findElements(By.partialLinkText("Administration")).isEmpty();
    }

    public AdministrationPage openAdministrationPage() {
        userPanel.findElement(By.partialLinkText("Administration")).click();
        return new AdministrationPage(driver);
    }
}
