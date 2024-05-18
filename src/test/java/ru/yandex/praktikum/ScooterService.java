package ru.yandex.praktikum;

import jdk.jfr.Description;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pageObject.MainPage;
import steps.Steps;

import java.time.Duration;

import static java.util.concurrent.TimeUnit.SECONDS;

public class ScooterService {

    private WebDriver webDriver;

    private static final String incorrectNumberOrder = "00000000000000";

    private static final String urlMainPageScooterService = "https://qa-scooter.praktikum-services.ru/";

    private static final String urlOrderPage = "https://qa-scooter.praktikum-services.ru/track?t=21746";

    private static final String urlDzenPage = "https://dzen.ru/?yredirect=tru";


    @Before
    public void setup() {
        webDriver = new ChromeDriver();
        webDriver.manage().timeouts().implicitlyWait(Duration.of(3, SECONDS.toChronoUnit()));
    }

    @Test
    @Description("Проверяем переход на главную страницу сервиса после тапа на лого Самокат в шапке сайта")
    public void shouldOpenMainPageScooterServiceAfterClickLogoInHeader() {
        MainPage objQuestionsPage = new MainPage(webDriver);
        Steps steps = new Steps(webDriver);
        steps.goToUrl(urlOrderPage);
        steps.windowMaximize();
        objQuestionsPage.waitDownloadOrderTrackPage();
        objQuestionsPage.clickElement(objQuestionsPage.logoScooterInHeader);
        Assert.assertEquals(urlMainPageScooterService, objQuestionsPage.getCurrentUrl());
    }

    @Test
    @Description("Проверяем переход на страницу яндекса после тапа на лого в Яндекс в шапке сайте")
    public void shouldOpenMainPageYandexAfterClickLogoInHeader() {
        MainPage objQuestionsPage = new MainPage(webDriver);
        objQuestionsPage.goToUrl(urlOrderPage);
        webDriver.manage().window().maximize();
        objQuestionsPage.waitDownloadOrderTrackPage();
        objQuestionsPage.clickElement(objQuestionsPage.logoYandexInHeader);
        objQuestionsPage.switchNewTabWindow();
        Assert.assertEquals(urlDzenPage, objQuestionsPage.getCurrentUrl());
    }

    @Test
    @Description("Проверяем переход на страницу статуса заказа c ошибкой, при указани неправильного номера заказа")
    public void shouldOpenOrderPageWithIncorrectNumberOrder() {
        MainPage objQuestionsPage = new MainPage(webDriver);
        objQuestionsPage.goToUrl(urlMainPageScooterService);
        webDriver.manage().window().maximize();
        objQuestionsPage.waitDownloadMainPage();
        objQuestionsPage.clickElement(objQuestionsPage.buttonOrderStatus);
        objQuestionsPage.elementIsDisplayed(objQuestionsPage.inputOrderStatusPlaceholder);
        objQuestionsPage.inputText(objQuestionsPage.inputOrderStatusPlaceholder, incorrectNumberOrder);
        objQuestionsPage.clickElement(objQuestionsPage.buttonGoSearchOrderByNumber);
        objQuestionsPage.elementIsDisplayed(objQuestionsPage.imgOnOrderPageWithIncorrectNumberOrder);
    }


    @After
    public void tearDown() {
        webDriver.quit();
    }
}

