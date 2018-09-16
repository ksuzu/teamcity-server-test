import java.io.File
import com.sun.deploy.cache.Cache.copyFile
import org.openqa.selenium.OutputType
import org.openqa.selenium.TakesScreenshot
import org.openqa.selenium.WebDriver
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ScreenShotRule(private val driver: WebDriver, private val destPath: String) : TestWatcher() {
    private val formatter = DateTimeFormatter.ofPattern("dd-MM-HH-mm-ss")

    private fun destinationFile(fileName: String): File {
        val absoluteFileName = "${destPath}/${fileName}.png"
        return File(absoluteFileName)
    }

    override fun failed(e: Throwable, description: Description) {
        val takesScreenshot = driver as TakesScreenshot

        try {
            val from = takesScreenshot.getScreenshotAs(OutputType.FILE)
            val to = destinationFile(
                    "${description.className}-${description.methodName}-${formatter.format(LocalDateTime.now())}")
            copyFile(from, to)
        } finally {
            driver.quit()
        }
    }

    override fun succeeded(description: Description?) {
        driver.quit()
    }
}