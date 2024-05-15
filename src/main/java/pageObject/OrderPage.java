package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class OrderPage {

    private final WebDriver webDriver;

    public By headerElementInMainPage = By.xpath(".//div[@class='Home_Header__iJKdX']");

    public By headerPageOrder = By.xpath(".//div[text()='Для кого самокат']");

    public By headerLastPageOrder = By.xpath(".//div[text()='Про аренду']");

    public By inputFirstName = By.xpath(".//input[@placeholder ='* Имя']");

    public By inputLastName = By.xpath(".//input[@placeholder ='* Фамилия']");

    public By inputLocate = By.xpath(".//input[@placeholder ='* Адрес: куда привезти заказ']");

    public By metroStation = By.xpath(".//input[@placeholder ='* Станция метро']");

    public By inputPhone = By.xpath(".//input[@placeholder ='* Телефон: на него позвонит курьер']");

    public By buttonContinue = By.xpath(".//button[text()='Далее']");

    public By tabIndex = By.xpath(".//li[@class='select-search__row']/button[@value='1']");

    public By dateDelivery = By.xpath(".//input[@placeholder ='* Когда привезти самокат']");

    public By rentalPeriod = By.className("Dropdown-control");

    public By selectRentalPeriod = By.xpath(".//div[@class='Dropdown-menu']/div[text()='сутки']");

    public By colourScooter = By.id("black");

    public By commentWithCourier = By.xpath(".//input[@placeholder='Комментарий для курьера']");

    public By returnOrderButton = By.xpath(".//button[@class='Button_Button__ra12g Button_Middle__1CSJM' and text()='Заказать']");

    public By headerOrderModal = By.xpath(".//div[@class='Order_Modal__YZ-d3']/div[text()='Хотите оформить заказ?']");

    public By submitButtonOnOrderModal = By.xpath(".//div[@class='Order_Buttons__1xGrp']/button[text()='Да']");

    public By finalOrderHeader = By.xpath(".//div[text()='Заказ оформлен']");

    public By cookieButton = By.xpath(".//button[text()='да все привыкли']");

    public By buttonOrderInMainPage = By.xpath(".//button[@class='Button_Button__ra12g' and text()='Заказать']");



    public OrderPage(WebDriver driver) {
        this.webDriver = driver;
    }

    public void goToUrl(String URL) {
        webDriver.get(URL);
    }

    public void waitDownloadPage(By element) {
        new WebDriverWait(webDriver, Duration.ofSeconds(6))
                .until(ExpectedConditions.visibilityOfElementLocated(element));
    }

    public void clickElementByXpath(String element) {
        webDriver.findElement(By.xpath(element)).click();
    }

    public void inputDataFirstForm(String firstName, String lastName, String city, String phone) {
        webDriver.findElement(inputFirstName).sendKeys(firstName);
        webDriver.findElement(inputLastName).sendKeys(lastName);
        webDriver.findElement(inputLocate).sendKeys(city);
        webDriver.findElement(metroStation).click();
        webDriver.findElement(tabIndex).click();
        webDriver.findElement(inputPhone).sendKeys(phone);
    }

    public void inputDataFirstFormWithoutMetroSelect(String firstName, String lastName, String city, String phone) {
        webDriver.findElement(inputFirstName).sendKeys(firstName);
        webDriver.findElement(inputLastName).sendKeys(lastName);
        webDriver.findElement(inputLocate).sendKeys(city);
        webDriver.findElement(inputPhone).sendKeys(phone);
    }

    public void clickDateDelivery(int daysPlus) {
        webDriver.findElement(dateDelivery).click();
        new WebDriverWait(webDriver, Duration.ofSeconds(6))
                .until(ExpectedConditions.visibilityOfElementLocated(By.className("react-datepicker__week")));
        webDriver.findElement(By.xpath(".//div[@class='react-datepicker__week']/div[text()='" + daysPlus + "']")).click();
    }

    public void inputDataLastForm(String comment) {
        webDriver.findElement(rentalPeriod).click();
        webDriver.findElement(selectRentalPeriod).click();
        webDriver.findElement(colourScooter).click();
        webDriver.findElement(commentWithCourier).sendKeys(comment);
        webDriver.findElement(returnOrderButton).click();
    }

    public void checkFinalOrder() {
        webDriver.findElement(finalOrderHeader).isDisplayed();
    }

    public void clickElement(By element) {
        webDriver.findElement(element).click();
    }

    public void checkTextInFindElement(String text) {
        webDriver.findElement(By.xpath(".//div[text()='" + text + "']"));
    }
}



