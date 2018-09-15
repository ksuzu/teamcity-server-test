import io.restassured.RestAssured

abstract class BaseApiTest {
    val apiVersion = "2018.1"
    protected val teamcityClientForDataPrepare = TeamcityClient("admin", "admin1")

    constructor() {
        RestAssured.baseURI = Settings.teamcityServerUrl
        RestAssured.port = Settings.teamcityServerPort
        RestAssured.basePath = String.format("/app/rest/%s", apiVersion)
    }

}
