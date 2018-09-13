import dto.*;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.UUID;

import static io.restassured.RestAssured.basic;
import static io.restassured.RestAssured.given;

public class TeamcityClient {
    private String username;
    private String password;
    private RequestSpecification spec;

    public TeamcityClient(String username, String password) {
        ResponseLoggingFilter responseLoggingFilter = new ResponseLoggingFilter();
        this.password = password;
        this.username = username;
        spec = new RequestSpecBuilder().addFilter(responseLoggingFilter)
                .setAuth(basic(username, password))
//                .setAccept(ContentType.fromContentType("application/json"))
                .build();
    }

    public BuildType createBuildType(BuildType buildType) {
        return given().spec(spec).body(buildType).
                expect().statusCode(200).
                when().post("/buildTypes").as(BuildType.class);
    }

    public BuildTypes getBuildTypes() {
        return given().spec(spec).
                expect().statusCode(200).
                when().get("/buildTypes").as(BuildTypes.class);
    }

    public Build queueBuild(BuildRequest build) {
        return given().spec(spec).body(build).
                expect().statusCode(200).
                when().post("/buildQueue").body().as(Build.class);
    }

    public void removeBuildFromQueue(Build build) {
        given().spec(spec).body(build).
                expect().statusCode(200).
                when().delete("/buildQueue").body().prettyPrint();
    }

    public BuildType createUniqueBuildType() {
        String buildName = UUID.randomUUID().toString();
        String projectId = "id1";
        BuildType buildTypeForCreation = new BuildType(buildName, projectId);
        return createBuildType(buildTypeForCreation);
    }

    public Long getQueueSize() {
        return given().spec(spec).when().get("/buildQueue").as(Builds.class).getCount();
    }
}
