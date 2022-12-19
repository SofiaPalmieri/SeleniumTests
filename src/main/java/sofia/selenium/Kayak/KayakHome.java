package sofia.selenium.Kayak;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

public class KayakHome extends KayakPage<KayakHome>{
    @FindBy(css = ".zEiP-origin input")
    protected WebElement originInput;

    @FindBy(css=".zEiP-destination input")
    protected WebElement destinationInput;

    @FindBy(className = "c8LPF-icon")
    protected List<WebElement> searchButtons;
    public final KayakCalendar calendar;

    public String originCode;

    public String destinationCode;

    public KayakHome(WebDriver driver){
        super(driver);
        PageFactory.initElements(driver, this);
        this.calendar = new KayakCalendar(driver);
    }

    public void setLocation(WebElement input, String name, int index){
        input.click();
        input.sendKeys(Keys.BACK_SPACE);
        input.sendKeys(Keys.BACK_SPACE);
        input.sendKeys(name);
        for(int i = 0; i <= index; i++){
            input.sendKeys(Keys.ARROW_DOWN);
        }
        input.sendKeys(Keys.ENTER);
    }

    public void setOrigin(String name, int index, String code){
        setLocation(originInput, name, index);
        this.originCode = code;
    }

    public void setDestination(String name, int index, String code){
        setLocation(destinationInput, name, index);
        this.destinationCode = code;
    }

    public void submitSearch(){
        WebDriverWait waitUntilSearchButtonIsClickeable = new WebDriverWait(driver, Duration.ofSeconds(5));
        waitUntilSearchButtonIsClickeable.until(ExpectedConditions.elementToBeClickable(searchButtons.get(searchButtons.size()-1)));
        searchButtons.get(searchButtons.size()-1).click();
    }

    public KayakSearchResults submitSimpleSearch(String origin, String destination, String originCode, String destinationCode, LocalDate startDate, LocalDate endDate){
        this.setOrigin(origin, 1, originCode);
        this.setDestination(destination, 1, destinationCode);
        this.calendar.reserveDates(startDate, endDate);
        this.submitSearch();
        return new KayakSearchResults(driver, originCode, destinationCode, startDate, endDate);
    }

    public KayakHome go(){
        driver.get("https://www.kayak.com.ar/");

        return this;
    }
}

