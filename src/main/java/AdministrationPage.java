import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AdministrationPage {
    private WebDriver driver;
    private static final By ADMIN_CONTEINER = By.id("admin-container");
    private static final By ADMIN_SIDEBAR = By.className("admin-sidebar");

    public AdministrationPage(WebDriver driver) {
        this.driver = driver;
    }

    public UsersModule openUsersModule() {
        driver.findElement(ADMIN_CONTEINER).findElement(ADMIN_SIDEBAR).findElement(By.partialLinkText("Users")).click();
        return new UsersModule(driver);
    }
}
