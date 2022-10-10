package main.java.commun;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

public class SetupTeardown {

    String browser = "chrome";
    protected WebDriver driver;
    protected String status;

    @BeforeMethod
    public void setUp() {
        switch (browser) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();


                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();

                break;
        }
        driver.get("https://www.carrefour.fr/");
        driver.manage().window().maximize();
//        System.out.println(System.getProperty("user.dir"));
//        System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir") + "\\chromedriver.exe");// mettre webdriver dans le systeme win 32
        driver.navigate();
        String browser = "chrome";

    }

    @AfterMethod
    public void teardown(ITestResult result) throws IOException, NoSuchAlgorithmException, KeyStoreException, InterruptedException, KeyManagementException {
        if (result.getStatus() == ITestResult.SUCCESS) {
            System.out.println("passed **");
            status = "PASSED";

        } else if (result.getStatus() == ITestResult.FAILURE) {
            System.out.println("Failed **");
            status = "FAILED";
            TakesScreenshot ts = (TakesScreenshot) driver;
//            File source = ts.getScreenshotAs(OutputType.FILE);
//            FileHandler.copy(source,new File(System.getProperty("user.dir")+"\\screshots\\capture.png"));
            File source = ts.getScreenshotAs(OutputType.FILE);
            FileHandler.copy(source,new File(System.getProperty("user.dir")+"\\screnshots\\"+result.getName()+".png"));
        } else if (result.getStatus() == ITestResult.SKIP) {
            System.out.println("Skiped**");
            status = "SKIPED";
        }
        ImportResultsXry res = new ImportResultsXry();
        res.generateJsonResults(status);
        res.RemonteResultats();
        driver.quit();


    }


}
