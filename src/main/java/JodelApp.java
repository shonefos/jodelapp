import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.remote.MobileCapabilityType;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.UUID;

/**
 * Created by Meridian on 10/3/2017.
 */
public class JodelApp {

    private AndroidDriver driver;
    private WebDriverWait wait;



    @BeforeClass
    public void setUp() throws MalformedURLException{

        //Setup Appium
        DesiredCapabilities caps = DesiredCapabilities.android();
        caps.setCapability(MobileCapabilityType.APP, "C:/Apks/com.tellm.android.app.apk");
        caps.setCapability(MobileCapabilityType.DEVICE_NAME,"LGD8559c0340a1");
        caps.setCapability("platformName", "Android");
        caps.setCapability("VERSION", "6.0");
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), caps);
        wait= new WebDriverWait(driver,30);

    }

    @Test
    public void JodelAppMobile() throws IOException, InterruptedException, URISyntaxException {
        // POKRETANJE APLIKACIJE
        WebDriverWait waitt = new WebDriverWait(driver,5000);
        Object o = waitt.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.name("Allow location")));

        try {
            if (driver.findElement(By.name("Allow location")).isDisplayed()) {
                System.out.println("Landing page is displayed!!!");
            }
        } catch (NoSuchElementException e) {
            System.out.println("ERROR - Landing page isn't displayed");
            File scrFile = driver.getScreenshotAs(OutputType.FILE);
            String fileName = UUID.randomUUID().toString();
            File targetFile = new File("target/screenshots/" + fileName + ".jpg");
            FileUtils.copyFile(scrFile, targetFile);
        }

        // PROVERA TEKSTA NA POCETNOJ STRANI
        String title = driver.findElement(By.name("The buzz on your campus")).getText();
        System.out.println("Title:" + title);

        String text = driver.findElement(By.name("Jodel needs your location to connect you with the community around you.")).getText();
        System.out.println("Text:" + text);

        String button = driver.findElement(By.name("Allow location")).getText();
        System.out.println("Button:" + button);

        driver.findElement(By.name("Allow location")).click();

        WebDriverWait waittt = new WebDriverWait(driver,5000);
        Object x = waittt.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("com.tellm.android.app:id/v_top_panel")));

        //PROVERA UCITAVANJA CHATA

        try {
            if (driver.findElement(By.id("com.tellm.android.app:id/feed_tab")).isDisplayed()) {
                System.out.println("Chat page is displayed!!!");
            }
        } catch (NoSuchElementException e) {
            System.out.println("ERROR - Chat page isn't displayed");
            File scrFile = driver.getScreenshotAs(OutputType.FILE);
            String fileName = UUID.randomUUID().toString();
            File targetFile = new File("target/screenshots/" + fileName + ".jpg");
            FileUtils.copyFile(scrFile, targetFile);
        }

        Thread.sleep(2000);


        //PRovera lokacije
        String location = driver.findElement(By.id("com.tellm.android.app:id/feed_title")).getText();
        driver.findElement(By.id("com.tellm.android.app:id/feed_tab")).click();
        Thread.sleep(3000);
        String current_town = driver.findElement(By.id("com.tellm.android.app:id/current_town")).getText();
        Thread.sleep(2000);

        if (location.equals(current_town)) {
            System.out.println("Location is good");
        }
        else
        {
            System.out.println("ERROR!!! - Lccation is not good");
        }

        driver.pressKeyCode(AndroidKeyCode.BACK);

        Thread.sleep(2000);

        // Glasanje
        String vote_number =  driver.findElement(By.xpath("//android.widget.LinearLayout[@index='3']/android.widget.TextView[@index='1']")).getText();
        driver.findElement(By.xpath("//android.widget.LinearLayout[@index='3']/android.widget.ImageButton[@index='0']")).click();
        String vote_number1 =  driver.findElement(By.xpath("//android.widget.LinearLayout[@index='3']/android.widget.TextView[@index='1']")).getText();

        double after = Double.parseDouble(vote_number.split(" ")[0]);
        double before = Double.parseDouble(vote_number1.split(" ")[0]);
        double result = (before - after);

        if (result == 0) {
            System.out.println("Vec ste glasali");
        } else if (result == 1) {
            System.out.println("Uspesno ste glasali");

        } else {
            System.out.println("ERROR!!! - napisati nesto");
        }

        // PIsanje chata

        driver.findElement(By.id("com.tellm.android.app:id/add_post_button")).click();

        try {
            if (driver.findElement(By.id("com.tellm.android.app:id/order_by_replies")).isDisplayed()) {
                driver.findElement(By.id("com.tellm.android.app:id/order_by_replies")).click();
            }
        } catch (NoSuchElementException e) {
            System.out.println("-- vec ste procitali terms and condition -- ");

        }

        driver.findElement(By.id("com.tellm.android.app:id/create_post_edit_text")).sendKeys("Jodel is cute!!!");
        String chat_text = driver.findElement(By.id("com.tellm.android.app:id/create_post_edit_text")).getText();
        driver.findElement(By.id("com.tellm.android.app:id/toolbar_send")).click();
        Thread.sleep(2000);
        String check_test = driver.findElement(By.xpath("//android.widget.RelativeLayout[@index='1']/android.widget.TextView[@index='0']")).getText();

        if (chat_text.equals(check_test)) {
            System.out.println("Text validno upisan");
        }
        else
        {
            System.out.println("ERROR - Text nije validno upisan");
        }

        driver.quit();





























    }



}
