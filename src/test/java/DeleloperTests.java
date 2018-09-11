import io.restassured.RestAssured;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.Cookie;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.basic;
import static io.restassured.RestAssured.given;

/**
 *
 */
public class DeleloperTests {
    String BASE_URL = "http://127.0.0.1";
    Integer serverPort = 9000;
    Cookie cookie;
    String apiVersion = "2018.1";

    @Before
    public void SetUp() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.port = serverPort;
        //TODO использовать спец метод для склеивания?
        RestAssured.basePath = String.format("/app/rest/%s", apiVersion);
//        RestAssured.basePath = "/app/rest/";
        RestAssured.authentication = basic("admin", "admin1");
        String TCSESSIONID = given().log().all().when().get("/server").getCookie("TCSESSIONID");

        cookie = new Cookie.Builder("TCSESSIONID", TCSESSIONID).build();
    }

    @Test
    public void getBuildsTest() {
        given().log().all().when().get("/builds").body().prettyPrint();
    }

    @Test
    //TODO почему-то не работает авторизация через куки
    public void queueNewBuild() {
        getQueueSize();
        Build testBuild = createSomeBuild();
        given().log().all().contentType("application/json").body(testBuild).
                expect().statusCode(200).
                when().post("/buildQueue").body().prettyPrint();
        getQueueSize();
    }

    @Test
    public void deleteExistedBuild() {
        getQueueSize();
        Build testBuild = createSomeBuild();
        queueBuild(testBuild);
        given().log().all().contentType("application/json").body(testBuild).
                expect().statusCode(200).
                when().delete("/buildQueue").body().prettyPrint();
        getQueueSize();
    }

    @Test
    public void getBuildTypesTest() {
        getBuildTypes();
    }

    public Build createSomeBuild() {
        String someBuildTypeId = getBuildTypes().getBuildType().stream().findAny().get().getId();
        return new Build(someBuildTypeId);
    }

    public BuildTypes getBuildTypes() {
        return given().filter(new ResponseLoggingFilter()).log().all().
                expect().statusCode(200).
                when().get("/buildTypes").as(BuildTypes.class);
    }


    public void queueBuild(Build build) {
        given().log().all().contentType("application/json").body(build).
                expect().statusCode(200).
                when().post("/buildQueue");
    }

    public void getQueueSize(){
        given().log().all().when().get("/buildQueue").body().prettyPrint();
    }
}
