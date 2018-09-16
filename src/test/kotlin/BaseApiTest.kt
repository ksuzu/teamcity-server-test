import io.restassured.RestAssured

abstract class BaseApiTest() {
    val apiVersion = "2018.1"
    protected val teamcityClientForDataPrepare = TeamcityClient(Settings.teamcityServerAdminUsername, Settings.teamcityServerAdminPassword)

    init {
        RestAssured.baseURI = Settings.teamcityServerUrlFromTests
        RestAssured.port = Settings.teamcityServerPort
        RestAssured.basePath = String.format("/app/rest/%s", apiVersion)
    }

}
