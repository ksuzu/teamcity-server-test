import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;

public class SeleniumTests extends BaseWebTest {
    @Test
    public void testCreateNewUserByDev() {
        LoginPage loginPage = new LoginPage(driver, BASE_URI);
        loginPage.loginAs("dev", "dev");
    }

    @Test
    public void testCreateNewUserByAdmin() {
        String testUser = UUID.randomUUID().toString();
        String testPassword = UUID.randomUUID().toString();
        LoginPage loginPage = new LoginPage(driver, BASE_URI);
        MainPage mainPage = loginPage.loginAs("ksu", "ksu1");
        AdministrationPage administrationPage = mainPage.getAdministrationPage();
        UsersModule userModuleBeforeAddingUser = administrationPage.openUsersModule();
        Long userCountBefore = userModuleBeforeAddingUser.getUsersCount();
        UserCreationPage userCreationPage = userModuleBeforeAddingUser.getUserCreationPage();
        userCreationPage.teamcityUser.sendKeys(testUser);
        userCreationPage.passwordInput.sendKeys(testPassword);
        userCreationPage.retypedPasswordInput.sendKeys(testPassword);
        userCreationPage.submitButton.click();

        UsersModule userModuleAfterAddingUser = administrationPage.openUsersModule();
        Long expectedCount = userCountBefore + 1;
        Assert.assertEquals(expectedCount, userModuleAfterAddingUser.getUsersCount());

    }
}