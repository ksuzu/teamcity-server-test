import io.restassured.RestAssured;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.Cookie;
import io.restassured.internal.mapping.Jackson2Mapper;
import io.restassured.mapper.factory.DefaultJackson2ObjectMapperFactory;
import io.restassured.mapper.factory.Jackson2ObjectMapperFactory;
import io.restassured.parsing.Parser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
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
//        RestAssured.basePath = String.format("/app/rest/%s", apiVersion);
        RestAssured.basePath = "/app/rest/";
        RestAssured.authentication = basic("ksu", "ksu1");
        String TCSESSIONID = given().log().all().when().get("/server").getCookie("TCSESSIONID");

        cookie = new Cookie.Builder("TCSESSIONID", TCSESSIONID).build();
    }

    @Test
    public void test() {
        String w = given().log().all().when().get("/builds").body().prettyPrint();
        System.out.println(w);

    }

    @Ignore
    @Test
    //TODO почему-то не работает авторизация через куки
    public void queueNewBuild() {
        Build newBuild = new Build();
        newBuild.setBuildTypeId("FirstProject_FirstConf");
        String x = given().log().all().contentType("application/json").body(newBuild).
                expect().statusCode(200).
                when().post("/buildQueue").body().prettyPrint();
        System.out.println(x);
    }

    @Test
    public void deleteExistedBuild() {
        Build build = new Build();
        build.setId(3L);
        given().log().all().contentType("application/json").body(build).
                expect().statusCode(200).
                when().delete("/buildQueue").body().prettyPrint();
    }

    @Test
    public void getBuildTypesTest() {
        BuildTypes d = getBuildTypes();
//        System.out.println(d.getBuildType().get(0).getId());
        System.out.println(d.getBuildType());
    }

    public Build createSomeBuild() {
        Build newBuild = new Build();
        newBuild.setBuildTypeId("FirstProject_FirstConf");
        return newBuild;
    }

    public BuildTypes getBuildTypes() {
        return given().filter(new ResponseLoggingFilter()).log().all().
                expect().statusCode(200).
                when().get("/buildTypes").as(BuildTypes.class);
    }

//    public String getBuildTypes() {
//        return given().filter(new ResponseLoggingFilter()).log().all().
//                expect().statusCode(200).
//                when().get("/buildTypes").xmlPath().getString("buildTypes.buildType[0].@id");
//    }

}
