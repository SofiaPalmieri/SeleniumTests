package sofia.selenium.Kayak;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class KayakSearchResults {
    @FindBy(className = "sR_k-date")
    WebElement startDate;

    WebDriver driver;

    public KayakSearchResults(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public String  getStartDateAsString(){
        return startDate.getText();
    }

}
