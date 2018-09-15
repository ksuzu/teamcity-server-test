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
    private static final By PASSWORD_ERROR = By.tagName("data-error");

    public UserCreationPage(WebDriver driver) {
        this.driver = driver;
    }

    public void fillUserField(String teamcityUsername) {
        driver.findElement(USERNAME).sendKeys(teamcityUsername);
    }

    public void fillNameField(String name) {
        driver.findElement(NAME).sendKeys(name);
    }

    public void fillPasswordField(String password) {
        driver.findElement(PASSWORD1).sendKeys(password);
    }

    public void fillRetypedPasswordField(String password) {
        driver.findElement(RETYPED_PASSWORD).sendKeys(password);
    }

    public void clickSubmitButton() {
        driver.findElement(SUBMIT_BUTTON).click();
    }

    public String tryLookForPasswordError() {
        return driver.findElement(By.className("input-wrapper_password1")).getAttribute("data-error");
    }
}
