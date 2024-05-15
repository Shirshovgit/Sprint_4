package ru.yandex.praktikum;

import jdk.jfr.Description;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pageObject.MainPage;

import java.time.Duration;

import static java.util.concurrent.TimeUnit.SECONDS;

public class ScooterService {

    private WebDriver webDriver;

    private static final String incorrectNumberOrder = "00000000000000";

    private static final String[] answerText = {"Сутки — 400 рублей. Оплата курьеру — наличными или картой.",
            "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями," +
                    " можете просто сделать несколько заказов — один за другим.",
            "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня." +
                    " Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру." +
                    " Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30.",
            "Только начиная с завтрашнего дня. Но скоро станем расторопнее.",
            "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010.",
            "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься" +
                    " без передышек и во сне. Зарядка не понадобится.",
            "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои.",
            "Да, обязательно. Всем самокатов! И Москве, и Московской области."
    };

    private static final String urlMainPageScooterService = "https://qa-scooter.praktikum-services.ru/";

    private static final String urlOrderPage = "https://qa-scooter.praktikum-services.ru/track?t=21746";

    private static final String urlDzenPage = "https://dzen.ru/?yredirect=tru";


    @Before
    public void setup() {
        webDriver = new ChromeDriver();
        webDriver.manage().timeouts().implicitlyWait(Duration.of(3, SECONDS.toChronoUnit()));
    }


    @Test
    @Description("Проверяем текст в разделе 'Вопросы о важном' открываемый по клику на вопросы")
    public void checkTextAfterClickInСhapterWithQuestionsTest() {
        MainPage objQuestionsPage = new MainPage(webDriver);
        objQuestionsPage.goToUrl(urlMainPageScooterService);
        webDriver.manage().window().maximize();
        objQuestionsPage.waitDownloadMainPage();
        objQuestionsPage.scrollTo();
        for (int i = 0; i < 8; i++) {
            objQuestionsPage.clickAccordionElement(i);
            objQuestionsPage.getTextInAccordion(i);
            String textAnswer = answerText[i];
            Assert.assertEquals(textAnswer, objQuestionsPage.getTextInAccordion(i));
        }
    }

    @Test
    @Description("Проверяем переход на главную страницу сервиса после тапа на лого Самокат в шапке сайта")
    public void shouldOpenMainPageScooterServiceAfterClickLogoInHeader() {
        MainPage objQuestionsPage = new MainPage(webDriver);
        objQuestionsPage.goToUrl(urlOrderPage);
        webDriver.manage().window().maximize();
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
    public void teardown() {
        webDriver.quit();
    }
}

