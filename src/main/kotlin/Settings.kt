import com.typesafe.config.ConfigFactory

object Settings {
    private val conf = ConfigFactory.load()

    val teamcityServerUrl: String = conf.getString("conf.teamcityServer.url")
    val teamcityServerPort: Int = conf.getInt("conf.teamcityServer.port")
    val webDriverPath: String = conf.getString("webDriverPath")

}

