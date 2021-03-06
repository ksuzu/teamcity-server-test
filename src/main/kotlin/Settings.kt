import com.typesafe.config.ConfigFactory

object Settings {
    private val conf = ConfigFactory.load().getConfig("conf")

    private val teamcityConfig = conf.getConfig("teamcityServer")
    val teamcityServerUrlFromDriver: String = teamcityConfig.getString("remote.url")
    val teamcityServerUrlFromTests: String = teamcityConfig.getString("local.url")
    val teamcityServerPort: Int = teamcityConfig.getInt("port")

    private val webdriverConfig = conf.getConfig("webDriver")
    val webDriverScreenshotPath: String = webdriverConfig.getString("screenshotPath")
    val webDriverUrl: String = webdriverConfig.getString("url")
    val webDriverPort: String = webdriverConfig.getString("port")

    private val usersConfig = conf.getConfig("users")
    val teamcityServerAdminUsername: String = usersConfig.getString("admin.username")
    val teamcityServerAdminPassword: String = usersConfig.getString("admin.password")
    val teamcityServerDevUsername: String = usersConfig.getString("dev.username")
    val teamcityServerDevPassword: String = usersConfig.getString("dev.password")
    val teamcityServerQuestUsername: String = usersConfig.getString("quest.username")
    val teamcityServerQuestPassword: String = usersConfig.getString("quest.password")

}

