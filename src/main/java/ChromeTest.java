import io.github.bonigarcia.wdm.WebDriverManager;
import org.checkerframework.checker.regex.qual.Regex;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.annotation.RegEx;
import java.security.Key;
import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class ChromeTest {

    WebDriver driver;
    static WebDriverManager wdm;

    @BeforeAll
    static void setupClass() {
        System.setProperty("webdriver.chrome.silentOutput", "true");
        wdm = WebDriverManager.getInstance("chrome");
        wdm.setup();
    }

    @BeforeEach
    void setupTest() {
        driver = wdm.create();
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }

    @Test
    void test() throws InterruptedException {
        // Exercise
        driver.get("https://www.google.com/");
        WebElement input = driver.findElement(By.name("q"));
        input.click();
        input.sendKeys("test");
        //input.sendKeys(Keys.ENTER);
        WebElement searchButton = driver.findElement(By.name("btnK"));
        input.sendKeys(Keys.ESCAPE);
        WebDriverWait waitForAutocompleteToClose = new WebDriverWait(driver, Duration.ofSeconds(2));
        waitForAutocompleteToClose.until(ExpectedConditions.elementToBeClickable(searchButton));
        searchButton.click();

        Assertions.assertTrue(driver.getTitle().contains("test"));

        WebElement result = driver.findElement(By.id("result-stats"));

        String string = result.getText();

        string = string.replaceAll(".* ([0-9.,]+) .*","$1").replaceAll("[.,]","");

        System.out.println(string);

        System.out.println(result.getText());


    }


}