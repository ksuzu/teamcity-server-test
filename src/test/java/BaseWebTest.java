import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public abstract class BaseWebTest {
    protected ChromeDriver driver;
    String BASE_URI = "http://127.0.0.1:9000";

    static {
        System.setProperty("webdriver.chrome.driver", "C:/chromedriver.exe");
    }

    @Before
    public void setUp() {
        driver = new ChromeDriver();
//        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
