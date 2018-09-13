import dto.*;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;

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
                .setContentType(ContentType.fromContentType("application/json"))
                .build();
    }

    public Project createUniqueProject() {
        String projectName = UUID.randomUUID().toString();
        ProjectRequest projectRequest = new ProjectRequest(projectName);
        return given().spec(spec).body(projectRequest).
                expect().statusCode(HttpStatus.SC_OK).
                when().post("/projects").as(Project.class);
    }

    public void createUniqueProject(Integer expectedResponseStatus) {
        String projectName = UUID.randomUUID().toString();
        ProjectRequest projectRequest = new ProjectRequest(projectName);
        given().spec(spec).body(projectRequest).
                expect().statusCode(expectedResponseStatus).
                when().post("/projects");
    }

    public BuildType createBuildType(BuildType buildType) {
        return given().spec(spec).body(buildType).
                expect().statusCode(HttpStatus.SC_OK).
                when().post("/buildTypes").as(BuildType.class);
    }

    public BuildTypes getBuildTypes() {
        return given().spec(spec).
                expect().statusCode(HttpStatus.SC_OK).
                when().get("/buildTypes").as(BuildTypes.class);
    }

    public Build queueBuild(BuildRequest build) {
        return given().spec(spec).body(build).
                expect().statusCode(HttpStatus.SC_OK).
                when().post("/buildQueue").body().as(Build.class);
    }

    public void removeBuildFromQueue(Build build) {
        given().spec(spec).body(build).
                expect().statusCode(HttpStatus.SC_OK).
                when().delete("/buildQueue");
    }

    public void removeBuildFromQueue(Build build, Integer expectedResponseStatus) {
        given().spec(spec).body(build).
                expect().statusCode(expectedResponseStatus).
                when().delete("/buildQueue");
    }

    public BuildType createUniqueBuildType() {
        String buildName = UUID.randomUUID().toString();
        String projectId = createUniqueProject().getId();
        BuildType buildTypeForCreation = new BuildType(buildName, projectId);
        return createBuildType(buildTypeForCreation);
    }

    public Long getQueueSize() {
        return given().spec(spec).expect().statusCode(HttpStatus.SC_OK).when().get("/buildQueue").as(Builds.class).getCount();
    }

    public void getBuilds() {
        given().spec(spec).expect().statusCode(HttpStatus.SC_OK).when().get("/builds");
    }
}
