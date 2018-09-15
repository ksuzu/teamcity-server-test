import org.junit.After
import org.junit.Before
import org.openqa.selenium.chrome.ChromeDriver

import java.util.concurrent.TimeUnit

abstract class BaseWebTest {
    protected val driver = ChromeDriver()
    val BASE_URI = "${Settings.teamcityServerUrl}:${Settings.teamcityServerPort}"

    @Before
    fun setUp() {
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS)
    }

    @After
    fun tearDown() {
        driver.quit()
    }

    companion object {

        init {
            Settings.teamcityServerPort
            System.setProperty("webdriver.chrome.driver", "C:/chromedriver.exe")
        }
    }
}
