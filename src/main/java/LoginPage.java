import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {
    private WebDriver driver;
    private static final By USERNAME_INPUT = By.id("username");
    private static final By PASSWORD_INPUT = By.id("password");
    private static final By LOG_IN_BUTTON = By.name("submitLogin");
    private String loginPageUrl;
    public WebElement passwordInput;
    public WebElement userInput;
    public WebElement loginButton;

    public LoginPage(WebDriver driver, String baseUri) {
        this.driver = driver;
        loginPageUrl = baseUri;//String.format("%s/login.html", baseUri);
        driver.get(loginPageUrl);

        this.userInput = driver.findElement(USERNAME_INPUT);
        this.passwordInput = driver.findElement(PASSWORD_INPUT);
        this.loginButton = driver.findElement(LOG_IN_BUTTON);
    }

    public MainPage loginAs(String user, String password) {
        userInput.sendKeys(user);
        passwordInput.sendKeys(password);
        loginButton.click();
        return new MainPage(driver);
    }
}
