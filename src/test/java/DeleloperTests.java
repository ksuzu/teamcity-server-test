import dto.Build;
import dto.BuildRequest;
import dto.BuildType;
import io.restassured.RestAssured;
import io.restassured.http.Cookie;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

/**
 *
 */
public class DeleloperTests {
    String BASE_URL = "http://127.0.0.1";
    Integer serverPort = 9000;
    Cookie cookie;
    String apiVersion = "2018.1";
    TeamcityClient teamcityClientByTestUser;
    TeamcityClient teamcityClientForDataPrepare;

    @Before
    public void SetUp() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.port = serverPort;
        RestAssured.basePath = String.format("/app/rest/%s", apiVersion);
        String TCSESSIONID = given().log().all().when().get("/server").getCookie("TCSESSIONID");

        cookie = new Cookie.Builder("TCSESSIONID", TCSESSIONID).build();
        teamcityClientByTestUser = new TeamcityClient("user1", "user1");
        teamcityClientForDataPrepare = new TeamcityClient("admin", "admin1");
    }

    @Test
    public void getBuildsTest() {
        teamcityClientByTestUser.getBuilds();
    }

    @Test
    public void createProject_ShouldBeForbidden(){
        teamcityClientByTestUser.createUniqueProject(HttpStatus.SC_FORBIDDEN);
    }

    @Test
    //TODO почему-то не работает авторизация через куки
    public void queueNewBuild() {
        //prepere test data
        Long buildNumberBefore = teamcityClientForDataPrepare.getQueueSize();
        BuildType uniqBuildType = teamcityClientForDataPrepare.createUniqueBuildType();
        BuildRequest buildRequest = createBuildRequest(uniqBuildType);
        //test
        Build createdBuild = teamcityClientByTestUser.queueBuild(buildRequest);
        Long buildNumberAfter = teamcityClientForDataPrepare.getQueueSize();
        Long expectedBuildNumberAfter = buildNumberBefore + 1;
        assertEquals(expectedBuildNumberAfter, buildNumberAfter);
        assertEquals(buildRequest.getBuildTypeId(), createdBuild.getBuildTypeId());
    }

    @Test
    public void createAndDeleteThisBuildFromBuildQueue() {
        //prepere test data
        BuildType uniqBuildType = teamcityClientForDataPrepare.createUniqueBuildType();
        BuildRequest buildRequest = createBuildRequest(uniqBuildType);
        Build buildInQueue = teamcityClientByTestUser.queueBuild(buildRequest);
        Long buildNumberBefore = teamcityClientForDataPrepare.getQueueSize();
        //test
        teamcityClientByTestUser.removeBuildFromQueue(buildInQueue);
        Long buildNumberAfter = teamcityClientForDataPrepare.getQueueSize();
        Long expectedBuildNumberAfter = buildNumberBefore - 1;
        assertEquals("The number of builds in buildQueue after removing from the queue was different of expected!",
                expectedBuildNumberAfter, buildNumberAfter);
    }

    @Test
    public void deleteExistedBuildFromBuildQueue_ShouldBeSuccess() {
        //prepere test data
        BuildType uniqBuildType = teamcityClientForDataPrepare.createUniqueBuildType();
        BuildRequest buildRequest = createBuildRequest(uniqBuildType);
        Build buildInQueue = teamcityClientForDataPrepare.queueBuild(buildRequest);
        Long buildNumberBefore = teamcityClientForDataPrepare.getQueueSize();
        //test
        teamcityClientByTestUser.removeBuildFromQueue(buildInQueue);
        Long buildNumberAfter = teamcityClientForDataPrepare.getQueueSize();
        Long expectedBuildNumberAfter = buildNumberBefore - 1;
        assertEquals("The number of builds in buildQueue after removing from the queue was different of expected!",
                expectedBuildNumberAfter, buildNumberAfter);
    }

    @Test
    public void deleteBuildCreatedAnotherUserFromBuildQueue_ShouldBeForbidden() {
        //prepere test data
        BuildType uniqBuildType = teamcityClientForDataPrepare.createUniqueBuildType();
        BuildRequest buildRequest = createBuildRequest(uniqBuildType);
        Build buildInQueue = teamcityClientForDataPrepare.queueBuild(buildRequest);
        Long buildNumberBefore = teamcityClientForDataPrepare.getQueueSize();
        //test
        teamcityClientByTestUser.removeBuildFromQueue(buildInQueue, HttpStatus.SC_FORBIDDEN);
        Long buildNumberAfter = teamcityClientForDataPrepare.getQueueSize();
        assertEquals("The number of builds in the queue before and after cancel-operation is not equal!",
                buildNumberBefore, buildNumberAfter);
    }

    @Test
    public void getBuildTypesTest() {
        teamcityClientByTestUser.getBuildTypes();
    }

    public BuildRequest createBuildRequest(BuildType buildType) {
        return new BuildRequest(buildType.getId());
    }
}
