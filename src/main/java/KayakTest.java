import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class KayakTest {
        WebDriver driver;

        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("yyyy-MM");
        DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyy");

        DateTimeFormatter shortDateFormatter = DateTimeFormatter.ofPattern("E. d/M");
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
            LocalDate startCalendar = LocalDate.now().plusMonths(2);
            LocalDate endCalendar = LocalDate.from(startCalendar).plusDays(5);
            driver.get("https://www.kayak.com.ar/");
            WebElement originInput = driver.findElement(By.cssSelector(".zEiP-origin input"));
            originInput.click();
            originInput.sendKeys(Keys.BACK_SPACE);
            originInput.sendKeys(Keys.BACK_SPACE);
            originInput.sendKeys("Buenos Aires");
            originInput.sendKeys(Keys.ARROW_DOWN);
            originInput.sendKeys(Keys.ENTER);
            WebElement destinationInput = driver.findElement(By.cssSelector(".zEiP-destination input"));
            destinationInput.sendKeys("Mar del Plata");
            destinationInput.sendKeys(Keys.ARROW_DOWN);
            destinationInput.sendKeys(Keys.ENTER);
            WebElement startDateField = driver.findElement(By.className("sR_k-value"));
            startDateField.click();
            this.selectOnCalendar(startCalendar);
            this.selectOnCalendar(endCalendar);
            WebElement searchButton = driver.findElement(By.className("c8LPF-icon"));
            WebDriverWait waitUntilSearchButtonIsClickeable = new WebDriverWait(driver, Duration.ofSeconds(2));
            waitUntilSearchButtonIsClickeable.until(ExpectedConditions.elementToBeClickable(searchButton));
            searchButton.click();
            WebElement resultStartDate = driver.findElement(By.className("sR_k-date"));
            Assertions.assertEquals(shortDateFormatter.format(startCalendar), resultStartDate.getText());

        }
        void selectOnCalendar(LocalDate date){
            String monthStr = monthFormatter.format(date);
            String dayStr = dayFormatter.format(date);
            List<WebElement> months = driver.findElements(By.cssSelector("[data-month]"));
            Optional<WebElement> monthElement = months.stream().filter(m -> monthStr.equals(m.getAttribute("data-month"))).findFirst();
            while (monthElement.isEmpty()){
                WebElement nextMonth = driver.findElement(By.cssSelector(".ATGJ-next-month button"));
                nextMonth.click();
                months = driver.findElements(By.cssSelector("[data-month]"));
                monthElement = months.stream().filter(m -> monthStr.equals(m.getAttribute("data-month"))).findFirst();
            }

            monthElement.get().findElement(By.cssSelector("[aria-label='"+dayStr+"']")).click();
        }

    }

