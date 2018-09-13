import dto.Build;
import dto.BuildRequest;
import dto.BuildType;
import io.restassured.RestAssured;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.Cookie;
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
    TeamcityClient teamcityClient;
    TeamcityClient teamcityClientForDataPrepare;


    //TODO заставить RestAssured подготовительные и тестовые запросы слать из-под разных пользователей?
    @Before
    public void SetUp() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.port = serverPort;
        RestAssured.basePath = String.format("/app/rest/%s", apiVersion);
//        RestAssured.basePath = "/app/rest/";
//        RestAssured.authentication = basic("admin", "admin1");
        String TCSESSIONID = given().log().all().when().get("/server").getCookie("TCSESSIONID");

        cookie = new Cookie.Builder("TCSESSIONID", TCSESSIONID).build();
        teamcityClient = new TeamcityClient("user1", "user1");
        teamcityClientForDataPrepare = new TeamcityClient("admin", "admin1");
    }

    @Test
    public void getBuildsTest() {
        given().log().all().when().get("/builds").body().prettyPrint();
    }

    @Test
    //TODO почему-то не работает авторизация через куки
    public void queueNewBuild() {
        //prepere test data
        Long buildNumberBefore = teamcityClientForDataPrepare.getQueueSize();
        BuildType uniqBuildType = teamcityClientForDataPrepare.createUniqueBuildType();
        BuildRequest buildRequest = createBuildRequest(uniqBuildType);
        //test
        Build createdBuild = teamcityClient.queueBuild(buildRequest);
        Long buildNumberAfter = teamcityClientForDataPrepare.getQueueSize();
        Long expectedBuildNumberAfter = buildNumberBefore + 1;
        assertEquals(expectedBuildNumberAfter, buildNumberAfter);
        assertEquals(buildRequest.getBuildTypeId(), createdBuild.getBuildTypeId());
    }

    @Test
    public void deleteExistedBuildFromBuildQueue() {
        //prepere test data
        BuildType uniqBuildType = teamcityClientForDataPrepare.createUniqueBuildType();
        BuildRequest buildRequest = createBuildRequest(uniqBuildType);
        Build buildInQueue = teamcityClientForDataPrepare.queueBuild(buildRequest);
        Long buildNumberBefore = teamcityClientForDataPrepare.getQueueSize();
        //test
        given().log().all().filter(new ResponseLoggingFilter()).contentType("application/json").body(buildInQueue).
                expect().statusCode(200).
                when().delete("/buildQueue").body().prettyPrint();
        Long buildNumberAfter = teamcityClientForDataPrepare.getQueueSize();
        Long expectedBuildNumberAfter = buildNumberBefore - 1;
        assertEquals("The number of builds in buildQueue did not decrease after removing from the queue",
                expectedBuildNumberAfter, buildNumberAfter);
    }

    @Test
    public void getBuildTypesTest() {
        teamcityClient.getBuildTypes();
    }

    public BuildRequest createBuildRequest(BuildType buildType) {
        return new BuildRequest(buildType.getId());
    }
}
