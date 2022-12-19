package sofia.selenium.Kayak;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class KayakSearchResults extends KayakPage<KayakSearchResults>{
    @FindBy(className = "sR_k-date")
    WebElement startDateElement;

    String originCode;

    String destinationCode;

    protected static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    LocalDate startDate;

    LocalDate endDate;

    public KayakSearchResults(WebDriver driver, String originCode, String destinationCode, LocalDate startDate, LocalDate endDate){
        super(driver);
        this.originCode = originCode;
        this.destinationCode = destinationCode;
        this.startDate = startDate;
        this.endDate = endDate;
        PageFactory.initElements(driver, this);
    }

    public KayakSearchResults go(){
        driver.get("https://www.kayak.com.ar/flights/" + originCode + "-" + destinationCode + "/" + dateFormatter.format(startDate) + "/" + dateFormatter.format(endDate) + "?sort=bestflight_a" );
        return this;
    }

    public String  getStartDateAsString(){
        return startDateElement.getText();
    }

}
