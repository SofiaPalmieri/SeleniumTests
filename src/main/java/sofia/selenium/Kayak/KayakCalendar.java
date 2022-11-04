package sofia.selenium.Kayak;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class KayakCalendar {
    @FindBy(className = "sR_k-value")
    WebElement startDateField;

    protected static DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("yyyy-MM");
    protected static DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyy");

    WebDriver driver;


    public KayakCalendar(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void reserveDates(LocalDate startCalendar, LocalDate endCalendar){
        startDateField.click();
        this.selectOnCalendar(startCalendar);
        this.selectOnCalendar(endCalendar);
    }
    private void selectOnCalendar(LocalDate date){
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
