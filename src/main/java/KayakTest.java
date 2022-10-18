import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;
import java.util.Objects;

public class KayakTest {
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
            driver.get("https://www.kayak.com.ar/");
            WebElement origenInput = driver.findElement(By.className("k_my-input"));
            origenInput.click();
            origenInput.sendKeys(Keys.BACK_SPACE);
            origenInput.sendKeys(Keys.BACK_SPACE);
            origenInput.sendKeys("Mar del Plata");
            origenInput.sendKeys(Keys.ARROW_DOWN);
            origenInput.sendKeys(Keys.ENTER);
            origenInput.sendKeys(Keys.TAB);
            origenInput.sendKeys(Keys.ENTER);
            origenInput.click();
            origenInput.sendKeys("Buenos Aires");
            origenInput.sendKeys(Keys.ARROW_DOWN);
            origenInput.sendKeys(Keys.ENTER);
            WebElement startDateField = driver.findElement(By.className("sR_k-value"));
            startDateField.click();
            WebElement month = driver.findElement(By.className("wHSr-monthName"));
            boolean containsMonth = month.getText().contains("diciembre 2022");
            while (!containsMonth){
                    WebElement nextMonth = driver.findElement(By.cssSelector("[aria-label='Mes siguiente']"));
                    nextMonth.click();
                WebElement month2 = driver.findElement(By.className("wHSr-monthName"));
                containsMonth = month2.getText().contains("diciembre 2022");
            }
            WebElement startDate = driver.findElement(By.cssSelector("[aria-label='9 de diciembre de 2022']"));
            startDate.click();
            WebElement endDate = driver.findElement(By.cssSelector("[aria-label='14 de diciembre de 2022']"));
            endDate.click();

            WebElement searchButton = driver.findElement(By.className("c8LPF-icon"));
            searchButton.click();

            WebElement results = driver.findElement(By.className("sR_k-date"));
            System.out.println(results.getText());
            if(results.getText().contains("vie. 9/12")){
                System.out.println("Pass");
            }else{
                System.out.println("Fail");
            }


        }


    }

