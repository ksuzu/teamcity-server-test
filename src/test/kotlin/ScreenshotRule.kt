import java.io.File
import java.io.IOException
import com.sun.deploy.cache.Cache.copyFile
import org.openqa.selenium.OutputType
import org.openqa.selenium.TakesScreenshot
import org.openqa.selenium.WebDriver
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class ScreenShotRule(private val driver: WebDriver, val destPath: String) : TestWatcher() {

    private fun destinationFile(fileName: String): File {
        val absoluteFileName = "${destPath}/${fileName}.png"

        return File(absoluteFileName)
    }

    override fun failed(e: Throwable, description: Description) {
        val takesScreenshot = driver as TakesScreenshot

        val scrFile = takesScreenshot.getScreenshotAs(OutputType.FILE)
        val destFile = destinationFile(description.getClassName() + "-" + description.getMethodName())
        destFile.createNewFile() // todo вероятно можно убрать
        try {
            copyFile(scrFile, destFile)
        } catch (ioe: IOException) {
            throw RuntimeException(ioe)
        }
    }
}