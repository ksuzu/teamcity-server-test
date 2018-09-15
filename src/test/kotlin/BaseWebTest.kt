import org.junit.After
import org.junit.Before
import org.openqa.selenium.chrome.ChromeDriver

import java.util.concurrent.TimeUnit

abstract class BaseWebTest {
    val driver = ChromeDriver()

    @Before
    fun setUp() {
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS)
    }

    @After
    fun tearDown() {
        driver.quit()
    }

    companion object {
        val BASE_URI = "${Settings.teamcityServerUrl}:${Settings.teamcityServerPort}"

        init {
            System.setProperty("webdriver.chrome.driver", Settings.webDriverPath)
        }
    }
}
