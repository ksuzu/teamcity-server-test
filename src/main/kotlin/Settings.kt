import com.typesafe.config.ConfigFactory

object Settings {
    private val conf = ConfigFactory.load()

    val teamcityServerUrlFromDriver: String = conf.getString("conf.teamcityServer.fromConteiners.url")
    val teamcityServerUrlFromTests: String = conf.getString("conf.teamcityServer.fromLocal.url")
    val teamcityServerPort: Int = conf.getInt("conf.teamcityServer.port")
    val webDriverPath: String = conf.getString("conf.webDriver.path")
    val webDriverScreenshotPath: String = conf.getString("conf.webDriver.screenshotPath")
    val webDriverUrl: String = conf.getString("conf.webDriver.url")
    val webDriverPort: String = conf.getString("conf.webDriver.port")
    val teamcityServerDevUsername: String = conf.getString("conf.users.dev.username")
    val teamcityServerAdminUsername: String = conf.getString("conf.users.admin.username")
    val teamcityServerDevPassword: String = conf.getString("conf.users.dev.password")
    val teamcityServerAdminPassword: String = conf.getString("conf.users.admin.password")

}

