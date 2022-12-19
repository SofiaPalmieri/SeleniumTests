package sofia.selenium.Kayak;

import org.openqa.selenium.WebDriver;

public abstract class KayakPage<T extends KayakPage<?>> {

   protected WebDriver driver;

    public abstract T go();

    public KayakPage(WebDriver driver){
        this.driver = driver;
    }
}
