package ru.yandex.praktikum;

import jdk.jfr.Description;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pageObject.OrderPage;

import java.time.Duration;
import java.util.Date;

import static java.util.concurrent.TimeUnit.SECONDS;

@RunWith(Parameterized.class)
public class OrderScooterSuccessFlow {

    private final String buttonOrder;
    private final String firstName;
    private final String lastName;
    private final String city;
    private final String phone;
    private final String commentCourier;

    private static final String urlScooterServiceMainPage = "https://qa-scooter.praktikum-services.ru/";

    private WebDriver webDriver;




    @Before
    public void setup() {
        webDriver = new ChromeDriver();
        webDriver.manage().timeouts().implicitlyWait(Duration.of(3, SECONDS.toChronoUnit()));
    }

    Date current = new Date();

    @Parameterized.Parameters
    public static Object[][] getCredentials() {
        return new Object[][]{
                {".//button[@class='Button_Button__ra12g' and text()='Заказать']",
                        "Алекс",
                        "Ширшов",
                        "Санкт-Петербург",
                        "79991112233",
                        "Комментарий"},
                {".//button[@class='Button_Button__ra12g Button_Middle__1CSJM' and text()='Заказать']",
                        "Петр-Петров",
                        "Shirshov",
                        "Москва",
                        "+79991112233",
                        "Комментарий"},
        };
    }

    public OrderScooterSuccessFlow(String buttonOrder, String firstName, String lastName, String city, String phone, String commentCourier) {
        this.buttonOrder = buttonOrder;
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.phone = phone;
        this.commentCourier = commentCourier;
    }

    @Test
    @Description("Проверяем успешный заказ самоката: заполнение двух форм + подтвреждение заказа")
    public void checkFlowOrderScooter() {
        OrderPage objOrderPage = new OrderPage(webDriver);
        objOrderPage.goToUrl(urlScooterServiceMainPage);
        webDriver.manage().window().maximize();
        objOrderPage.waitDownloadPage(objOrderPage.headerElementInMainPage);
        objOrderPage.clickElement(objOrderPage.cookieButton);
        objOrderPage.clickElementByXpath(buttonOrder);
        objOrderPage.waitDownloadPage(objOrderPage.headerPageOrder);
        objOrderPage.inputDataFirstForm(firstName, lastName, city, phone);
        objOrderPage.clickElement(objOrderPage.buttonContinue);
        objOrderPage.waitDownloadPage(objOrderPage.headerLastPageOrder);
        objOrderPage.clickDateDelivery(getPlusDays());
        objOrderPage.inputDataLastForm(commentCourier);
        objOrderPage.waitDownloadPage(objOrderPage.headerOrderModal);
        objOrderPage.clickElement(objOrderPage.submitButtonOnOrderModal);
        objOrderPage.checkFinalOrder();
    }

    @Test
    @Description("Проверяем отображение ошибки у полей заказа самоката")
    public void shouldErrorInInputOnFormOrder() {
        OrderPage objOrderPage = new OrderPage(webDriver);
        objOrderPage.goToUrl(urlScooterServiceMainPage);
        webDriver.manage().window().maximize();
        objOrderPage.waitDownloadPage(objOrderPage.headerElementInMainPage);
        objOrderPage.clickElementByXpath(buttonOrder);

    }



    public int getPlusDays() {
        current = DateUtils.addDays(current, 1);
        return current.getDate();
    }

    @After
    public void teardown() {
        webDriver.quit();
    }
}
