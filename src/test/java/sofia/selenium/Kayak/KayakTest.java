package sofia.selenium.Kayak;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class KayakTest {
        WebDriver driver;
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
            KayakHome page = new KayakHome(driver);
            page.submitSimpleSearch("Buenos Aires", "Mar del Plata", startCalendar, endCalendar);
            KayakSearchResults results = new KayakSearchResults(driver);
            Assertions.assertEquals(shortDateFormatter.format(startCalendar), results.getStartDateAsString());

        }



    }

