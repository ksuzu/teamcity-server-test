import dto.*
import io.restassured.builder.RequestSpecBuilder
import io.restassured.filter.log.ResponseLoggingFilter
import io.restassured.http.ContentType
import io.restassured.specification.RequestSpecification
import org.apache.http.HttpStatus

import java.util.UUID

import io.restassured.RestAssured.basic
import io.restassured.RestAssured.given

class TeamcityClient(userCredentials: UserCredentials) {
    private val spec: RequestSpecification = RequestSpecBuilder().addFilter(ResponseLoggingFilter())
            .setAuth(basic(userCredentials.username, userCredentials.password))
            .setContentType(ContentType.fromContentType("application/json"))
            .build()

    fun getBuildTypes(): BuildTypes {
        return given().spec(spec).expect().statusCode(HttpStatus.SC_OK).`when`().get("/buildTypes").`as`(BuildTypes::class.java)
    }

    fun getBuildQueueSize(): Long {
        return getBuildQueue().count
    }

    fun getBuildQueue(): Builds {
        return given().spec(spec).expect().statusCode(HttpStatus.SC_OK).`when`().get("/buildQueue").`as`(Builds::class.java)
    }

    fun getBuilds(): Builds {
        return given().spec(spec).expect().statusCode(HttpStatus.SC_OK).`when`().get("/builds").`as`(Builds::class.java)
    }

    fun createUniqueProject(): Project {
        val projectName = UUID.randomUUID().toString()
        val projectRequest = ProjectRequest(projectName)
        return given().spec(spec).body(projectRequest).expect().statusCode(HttpStatus.SC_OK).`when`().post("/projects").`as`(Project::class.java)
    }

    fun createUniqueProject(expectedResponseStatus: Int?) {
        val projectName = UUID.randomUUID().toString()
        val projectRequest = ProjectRequest(projectName)
        given().spec(spec).body(projectRequest).expect().statusCode(expectedResponseStatus!!).`when`().post("/projects")
    }

    fun createBuildType(buildType: CreateBuildTypeRequest): BuildType {
        return given().spec(spec).body(buildType).expect().statusCode(HttpStatus.SC_OK).`when`().post("/buildTypes").`as`(BuildType::class.java)
    }

    fun queueBuild(build: CreateBuildRequest): Build {
        return given().spec(spec).body(build).expect().statusCode(HttpStatus.SC_OK).`when`().post("/buildQueue").`as`(Build::class.java)
    }

    fun removeBuildFromQueue(build: Build, expectedResponseStatus: Int?) {
        given().spec(spec).body(build).expect().statusCode(expectedResponseStatus!!).`when`().delete("/buildQueue")
    }

    fun createUniqueBuildType(): BuildType {
        val buildName = UUID.randomUUID().toString()
        val projectId = createUniqueProject().id
        val buildTypeForCreation = CreateBuildTypeRequest(buildName, projectId)
        return createBuildType(buildTypeForCreation)
    }
}
