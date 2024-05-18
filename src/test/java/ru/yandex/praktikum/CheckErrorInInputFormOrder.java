package ru.yandex.praktikum;

import jdk.jfr.Description;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pageObject.OrderPage;
import steps.Steps;

import java.time.Duration;

import static java.util.concurrent.TimeUnit.SECONDS;

public class CheckErrorInInputFormOrder {

    private WebDriver webDriver;

    private static final String urlScooterServiceMainPage = "https://qa-scooter.praktikum-services.ru/";

    private static final String[] errorTextInInput = {
            "Введите корректное имя",
            "Введите корректную фамилию",
            "Введите корректный адрес",
            "Выберите станцию",
            "Введите корректный номер"};

    private static final String[] errorDataForInputFormOrder = {
            "ErrorFirstName",
            "ErrorLastName",
            "ErrorCity",
            "ErrorPhone"};

    @Before
    public void setup() {
        webDriver = new ChromeDriver();
        webDriver.manage().timeouts().implicitlyWait(Duration.of(3, SECONDS.toChronoUnit()));
    }

    @Test
    @Description("Проверяем отображение ошибки у полей формы заказа")
    public void shouldErrorInInputOnFirstFormOrder() {
        OrderPage objOrderPage = new OrderPage(webDriver);
        Steps steps = new Steps(webDriver);
        steps.goToUrl(urlScooterServiceMainPage);
        steps.windowMaximize();
        objOrderPage.waitDownloadPage(objOrderPage.headerElementInMainPage);
        objOrderPage.clickElement(objOrderPage.buttonOrderInMainPage);
        objOrderPage.inputDataFirstFormWithoutMetroSelect(
                errorDataForInputFormOrder[0],
                errorDataForInputFormOrder[1],
                errorDataForInputFormOrder[2],
                errorDataForInputFormOrder[3]);
        objOrderPage.clickElement(objOrderPage.buttonContinue);
        for (int i = 0; i < 5; i++) {
            objOrderPage.checkTextInFindElement(errorTextInInput[i]);
        }
    }


    @After
    public void tearDown() {

        webDriver.quit();
    }
}
