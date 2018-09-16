import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.RemoteWebDriver
import java.net.URL

import java.util.concurrent.TimeUnit

abstract class BaseUITest {
    val options = ChromeOptions()
        .addArguments("--headless")
    val driver = RemoteWebDriver(URL("${Settings.webDriverUrl}:${Settings.webDriverPort}/wd/hub"), options)

    val teamcityClientForDataPrepare = TeamcityClient(Settings.teamcityServerAdminUsername,
            Settings.teamcityServerAdminPassword)
    val devUsername = Settings.teamcityServerDevUsername
    val devPassword = Settings.teamcityServerDevPassword
    val adminUsername = Settings.teamcityServerAdminUsername
    val adminPassword = Settings.teamcityServerAdminPassword

    @get:Rule
    val screenShootRule = ScreenShotRule(driver, Settings.webDriverScreenshotPath)

    @Before
    fun setUp() {
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS)
    }

    companion object {
        val TEAMCITY_BASE_URI_FOR_DRIVER = "${Settings.teamcityServerUrlFromDriver}:${Settings.teamcityServerPort}"
        val TEAMCITY_BASE_URI_FOR_LOCAL = "${Settings.teamcityServerUrlFromTests}:${Settings.teamcityServerPort}"
    }
}
