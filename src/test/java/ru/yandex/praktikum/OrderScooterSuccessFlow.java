package ru.yandex.praktikum;

import jdk.jfr.Description;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pageObject.OrderPage;
import steps.Steps;

import java.time.Duration;
import java.util.Date;

import static java.util.concurrent.TimeUnit.SECONDS;

@RunWith(Parameterized.class)
public class OrderScooterSuccessFlow {

    private WebDriver webDriver;
    private final By buttonOrder;
    private final String firstName;
    private final String lastName;
    private final String city;
    private final String phone;
    private final String commentCourier;

    private static final String urlScooterServiceMainPage = "https://qa-scooter.praktikum-services.ru/";

    private static final By buttonOrderInHeaderPage = OrderPage.buttonOrderInHeaderPage;

    private static final By getButtonOrderInFooterPage = OrderPage.getButtonOrderInFooterPage;

    @Before
    public void setup() {
        webDriver = new ChromeDriver();
        webDriver.manage().timeouts().implicitlyWait(Duration.of(3, SECONDS.toChronoUnit()));
    }

    Date current = new Date();

    @Parameterized.Parameters
    public static Object[][] getCredentials() {
        return new Object[][]{
                {buttonOrderInHeaderPage,
                        "Алекс",
                        "Ширшов",
                        "Санкт-Петербург",
                        "79991112233",
                        "Комментарий"},
                {getButtonOrderInFooterPage,
                        "Петр-Петров",
                        "Shirshov",
                        "Москва",
                        "+79991112233",
                        "Комментарий"},
        };
    }

    public OrderScooterSuccessFlow(By buttonOrder, String firstName, String lastName, String city, String phone, String commentCourier) {
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
        Steps steps = new Steps(webDriver);
        steps.goToUrl(urlScooterServiceMainPage);
        steps.windowMaximize();
        objOrderPage.waitDownloadPage(objOrderPage.headerElementInMainPage);
        objOrderPage.clickElement(objOrderPage.cookieButton);
        objOrderPage.clickElement(buttonOrder);
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
        Steps steps = new Steps(webDriver);
        steps.goToUrl(urlScooterServiceMainPage);
        steps.windowMaximize();
        objOrderPage.waitDownloadPage(objOrderPage.headerElementInMainPage);
        objOrderPage.clickElement(buttonOrder);

    }

    public int getPlusDays() {
        current = DateUtils.addDays(current, 1);
        return current.getDate();
    }

    @After
    public void tearDown() {
        webDriver.quit();
    }
}
