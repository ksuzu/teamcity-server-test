import com.typesafe.config.ConfigFactory

object Settings {
    private val conf = ConfigFactory.load()

    val teamcityServerUrl: String = conf.getString("conf.teamcityServer.url")
    val teamcityServerPort: Int = conf.getInt("conf.teamcityServer.port")
    val webDriverPath: String = conf.getString("conf.webDriver.path")
    val webDriverScreenshotPath: String = conf.getString("conf.webDriver.screenshotPath")
    val webDriverUrl: String = conf.getString("conf.webDriver.url")
    val webDriverPort: String = conf.getString("conf.webDriver.port")

}

