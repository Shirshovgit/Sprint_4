package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;

public class MainPage {

    private final WebDriver webDriver;

    public MainPage(WebDriver driver) {
        this.webDriver = driver;
    }

    private final By headerPageQuestions = By.xpath(".//div[@class='Home_SubHeader__zwi_E' and text()='Вопросы о важном']");

    public By logoScooterInHeader = By.xpath(".//img[@alt='Scooter']");

    public By logoYandexInHeader = By.xpath(".//img[@alt='Yandex']");

    public By buttonOrderStatus = By.xpath(".//button[text()='Статус заказа']");

    public By inputOrderStatusPlaceholder = By.xpath(".//input[@placeholder='Введите номер заказа']");

    public By buttonGoSearchOrderByNumber = By.xpath(".//button[text()='Go!']");

    public By imgOnOrderPageWithIncorrectNumberOrder = By.xpath(".//img[@alt='Not found']");


    public void goToUrl(String URL) {
        webDriver.get(URL);
    }


    public void scrollTo() {
        WebElement element = webDriver.findElement(headerPageQuestions);
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView();", element);
    }

    public void clickAccordionElement(int position) {
        WebElement element = webDriver.findElement(By.xpath(".//div[@id='accordion__heading-" + position + "']"));
        element.click();
    }

    public void waitDownloadMainPage() {
        new WebDriverWait(webDriver, Duration.ofSeconds(6))
                .until(ExpectedConditions.visibilityOfElementLocated(By.className("Home_Header__iJKdX")));
    }

    public void waitDownloadOrderTrackPage() {
        new WebDriverWait(webDriver, Duration.ofSeconds(6))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//input[@value='21746']")));
    }

    public String getTextInAccordion(int position) {
        WebElement text = webDriver.findElement(By.xpath(".//div[@id='accordion__panel-" + position + "']"));
        return text.getText();
    }

    public String getCurrentUrl() {
        return webDriver.getCurrentUrl();
    }

    public void switchNewTabWindow() {
        ArrayList<String> newTab = new ArrayList<>(webDriver.getWindowHandles());
        webDriver.switchTo().window(newTab.get(1));
    }

    public void clickElement(By element) {
        webDriver.findElement(element).click();
    }

    public void elementIsDisplayed(By element){
        webDriver.findElement(element).isDisplayed();
    }

    public void inputText(By element, String text){
        webDriver.findElement(element).sendKeys(text);
    }

}
