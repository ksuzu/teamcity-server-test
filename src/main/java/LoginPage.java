import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    private WebDriver driver;
    private static final By USERNAME_INPUT = By.id("username");
    private static final By PASSWORD_INPUT = By.id("password");
    private static final By LOG_IN_BUTTON = By.name("submitLogin");
    private String loginPageUrl;

    public LoginPage(WebDriver driver, String baseUri) {
        this.driver = driver;
        loginPageUrl = baseUri;//String.format("%s/login.html", baseUri);
    }

    public void open(){
        driver.get(loginPageUrl);
    }

    public OverviewPage loginAs(String user, String password) {
        driver.findElement(USERNAME_INPUT).sendKeys(user);
        driver.findElement(PASSWORD_INPUT).sendKeys(password);
        driver.findElement(LOG_IN_BUTTON).click();
        return new OverviewPage(driver);
    }
}
