import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MainPage {
    private WebDriver driver;
    private String mainPageUrl;
    private static final By USER_PANEL = By.id("userPanel");
    public WebElement userPanel;

    public MainPage(WebDriver driver) {
        this.driver = driver;
        userPanel = driver.findElement(USER_PANEL);
    }

    public MainPage(WebDriver driver, String baseUri) {
        this.driver = driver;
        userPanel = driver.findElement(USER_PANEL);
        mainPageUrl = baseUri;
    }

    public WebElement administrationPageLink() {
        //FIXME искать по тексту не очень
        return userPanel.findElement(By.partialLinkText("Administration"));
    }

    public AdministrationPage getAdministrationPage() {
        //FIXME искать по тексту не очень
        userPanel.findElement(By.partialLinkText("Administration")).click();
        return new AdministrationPage(driver);
    }
}
