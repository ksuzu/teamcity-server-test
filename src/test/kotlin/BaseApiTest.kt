import dto.UserCredentials
import io.restassured.RestAssured
import org.junit.BeforeClass

abstract class BaseApiTest() {
    protected val teamcityClientForDataPrepare = TeamcityClient(UserCredentials(Settings.teamcityServerAdminUsername,
            Settings.teamcityServerAdminPassword))
    protected val teamcityClientByDevUser = TeamcityClient(UserCredentials(Settings.teamcityServerDevUsername,
            Settings.teamcityServerDevPassword))
    protected val teamcityClientByAdminUser = TeamcityClient(UserCredentials(Settings.teamcityServerAdminUsername,
            Settings.teamcityServerAdminPassword))

    companion object {
        val apiVersion = "2018.1"

        @BeforeClass
        @JvmStatic
        fun beforeAll() {
            RestAssured.baseURI = Settings.teamcityServerUrlFromTests
            RestAssured.port = Settings.teamcityServerPort
            RestAssured.basePath = "/app/rest/${apiVersion}"
        }
    }
}
