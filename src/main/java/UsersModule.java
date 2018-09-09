import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class UsersModule {
    private WebDriver driver;
    private static final By USER_TABLE = By.id("userTableInner");
    public WebElement userCounter;
    public WebElement createUserButton;

    public UsersModule(WebDriver driver) {
        this.driver = driver;
        this.createUserButton = driver.findElement(USER_TABLE).findElement(By.partialLinkText("Create user account"));
        this.userCounter = driver.findElement(USER_TABLE).findElement(By.className("usersActions")).findElement(By.className("pagerDesc")).findElement(By.tagName("strong"));
    }

    public UserCreationPage getUserCreationPage() {
        createUserButton.click();
        return new UserCreationPage(driver);
    }

    public Long getUsersCount(){
        return Long.valueOf(userCounter.getText());
    }
}
