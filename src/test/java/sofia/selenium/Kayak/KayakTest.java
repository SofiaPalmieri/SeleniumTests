package sofia.selenium.Kayak;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.WebDriver;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;


//@Execution(ExecutionMode.CONCURRENT)

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

    //@Execution(ExecutionMode.SAME_THREAD)
    //@Timeout(value = 10000, unit = TimeUnit.MILLISECONDS, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    @ParameterizedTest
    @MethodSource("dataSource")
    void testSearchFromHomePage(LocalDate startCalendar, LocalDate endCalendar, String origin, String destination, String originCode, String destinationCode) throws InterruptedException {
        KayakHome page = new KayakHome(driver);
        KayakSearchResults results = page.go().submitSimpleSearch(origin, destination, originCode, destinationCode, startCalendar, endCalendar);
        Assertions.assertEquals(shortDateFormatter.format(startCalendar), results.getStartDateAsString());

    }
    //@Execution(ExecutionMode.SAME_THREAD)
    //@Timeout(value = 10000, unit = TimeUnit.MILLISECONDS, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    @ParameterizedTest
    @MethodSource("dataSource")
    void testSearchFromURL(LocalDate startCalendar, LocalDate endCalendar, String origin, String destination, String originCode, String destinationCode) {
        KayakSearchResults results = new KayakSearchResults(driver, originCode, destinationCode, startCalendar, endCalendar);
        results.go();
        Assertions.assertEquals(shortDateFormatter.format(startCalendar), results.getStartDateAsString());

    }

    private static Stream<Arguments> dataSource() {
        LocalDate startCalendar = LocalDate.now().plusMonths(2);
        LocalDate endCalendar = LocalDate.from(startCalendar).plusDays(5);

        return Stream.of(
                Arguments.of(startCalendar, endCalendar, "Buenos Aires", "Mar del Plata", "BUE", "MDQ"),
                Arguments.of(startCalendar, endCalendar, "Colombia", "Nueva York", "BOG", "NYC")
        );
    }


}

