import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class UserCreationPage {
    private WebDriver driver;
    private static final By USERNAME = By.id("input_teamcityUsername");
    private static final By NAME = By.id("name");
    private static final By PASSWORD1 = By.id("password1");
    private static final By RETYPED_PASSWORD = By.id("retypedPassword");
    private static final By SUBMIT_BUTTON = By.name("submitCreateUser");
    public WebElement teamcityUser;
    public WebElement name;
    public WebElement passwordInput;
    public WebElement retypedPasswordInput;
    public WebElement submitButton;

    public UserCreationPage(WebDriver driver) {
        this.driver = driver;

        this.teamcityUser = driver.findElement(USERNAME);
        this.name = driver.findElement(NAME);
        this.passwordInput = driver.findElement(PASSWORD1);
        this.retypedPasswordInput = driver.findElement(RETYPED_PASSWORD);
        this.submitButton = driver.findElement(SUBMIT_BUTTON);
    }
}
