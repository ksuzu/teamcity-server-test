import com.fasterxml.jackson.annotation.JsonTypeInfo;
import dto.BuildType;
import dto.Builds;
import io.restassured.RestAssured;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.UUID;

@Ignore
public class SeleniumTests extends BaseWebTest {
    String adminUsername = "admin";
    String adminPassword = "admin1";
    String devUsername = "user1";
    String devPassword = "user1";
    TeamcityClient teamcityClientForDataPrepare;

    @Before
    public void initTeamcityApiClient() {
        RestAssured.baseURI = getBASE_URI();
        RestAssured.basePath = "/app/rest/";
        teamcityClientForDataPrepare = new TeamcityClient("admin", "admin1");
    }

    @Test
    public void testAvailabilityAdministrationModuleByDevUser() {
        LoginPage loginPage = new LoginPage(getDriver(), getBASE_URI());
        loginPage.open();
        loginPage.loginAs(devUsername, devPassword);
        Assert.assertEquals(true, new UserPanel(getDriver()).isAdministrationPageLinkAbset());
    }

    @Test
    public void testCreateNewUserByAdmin() {
        String testUser = UUID.randomUUID().toString();
        String testPassword = UUID.randomUUID().toString();
        LoginPage loginPage = new LoginPage(getDriver(), getBASE_URI());
        loginPage.open();
        loginPage.loginAs(adminUsername, adminPassword);
        AdministrationPage administrationPage = new UserPanel(getDriver()).openAdministrationPage();
        UsersModule userModuleBeforeAddingUser = administrationPage.openUsersModule();
        Long userCountBefore = userModuleBeforeAddingUser.getUsersCount();
        UserCreationPage userCreationPage = userModuleBeforeAddingUser.getUserCreationPage();
        userCreationPage.fillUserField(testUser);
        userCreationPage.fillPasswordField(testPassword);
        userCreationPage.fillRetypedPasswordField(testPassword);
        userCreationPage.clickSubmitButton();

        UsersModule userModuleAfterAddingUser = administrationPage.openUsersModule();
        Long expectedCount = userCountBefore + 1;
        Assert.assertEquals(expectedCount, userModuleAfterAddingUser.getUsersCount());
    }

    @Test
    public void incorrectEnterRetypePasswordOnUserCreation_shouldGiveError() {
        String testUser = UUID.randomUUID().toString();
        String testPassword = UUID.randomUUID().toString();
        LoginPage loginPage = new LoginPage(getDriver(), getBASE_URI());
        loginPage.open();
        loginPage.loginAs(adminUsername, adminPassword);
        AdministrationPage administrationPage = new UserPanel(getDriver()).openAdministrationPage();
        UsersModule userModuleBeforeAddingUser = administrationPage.openUsersModule();
        Long userCountBefore = userModuleBeforeAddingUser.getUsersCount();
        UserCreationPage userCreationPage = userModuleBeforeAddingUser.getUserCreationPage();
        userCreationPage.fillUserField(testUser);
        userCreationPage.fillPasswordField(testPassword);
        userCreationPage.fillRetypedPasswordField("incorrectPwd");
        userCreationPage.clickSubmitButton();

        Assert.assertEquals("Passwords mismatch", userCreationPage.tryLookForPasswordError());

        new UserPanel(getDriver()).openAdministrationPage();
        UsersModule userModuleAfterAddingUser = administrationPage.openUsersModule();
        Assert.assertEquals(userCountBefore, userModuleAfterAddingUser.getUsersCount());
    }

    @Test
    public void runBuildWithParameters() {
        BuildType testBuildType = teamcityClientForDataPrepare.createUniqueBuildType();
        String testBuildTypeName = testBuildType.getName();
        String testProjectName = testBuildType.getProjectName();
        LoginPage loginPage = new LoginPage(getDriver(), getBASE_URI());
        loginPage.open();
        OverviewPage overviewPage = loginPage.loginAs(adminUsername, adminPassword);
        CustomBuildDialog dialog = overviewPage.navigateToProjectByName(testProjectName).navigateToBuildTypeByName(testBuildTypeName).
                navigateToCustomBuildDialog();
        dialog.navigateToParameters().addNewConfigurationParameter("name", "value");
        dialog.submitRunBuild();
        Boolean d = teamcityClientForDataPrepare.getBuildQueue().getBuild().stream().
                anyMatch((x) -> x.getBuildTypeId().equals(testBuildType.getId()));
        Assert.assertEquals(true, d);
    }
}