package tests;

import java.util.concurrent.TimeUnit;
import laba.LoginPage;
import laba.RegistrationPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import static org.junit.Assert.*;

public class LoginPageTest {

    private static WebDriver driver;
    private static LoginPage loginPage;
    private final String ERROR_MESSAGE = "//*[@id=\"content\"]/div";
    private final By ERROR_LOCATOR = By.xpath(ERROR_MESSAGE);

    @Before
    public void setUp() {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        loginPage = new LoginPage(driver);
    }

    @After
    public void closeBrowser() {
        if (driver != null) {
            driver.close();
            driver.quit();
        }
    }

    @Test
    public void test_05_goToLoginPage() {        
        String titleActual = driver.getTitle();
        String titleExpected = "Login Page";
        String headerActual = driver.findElement(By.id("header")).getText();
        String headerExpected = "Software Logo";
        assertEquals(titleExpected, titleActual);
        assertEquals(headerExpected, headerActual);
    }

    @Test
    public void test_06_checkValidationLoginForm() {
        loginPage.authorization("!@ #$$% ^&** ))(", "noname");
        String messageActual = driver.findElement(ERROR_LOCATOR).getText();
        String messageExpected = "Your login attempt was not successful, try again.\n"
                + "Caused : Username/Password entered is incorrect.";
        assertEquals(messageExpected, messageActual);
    }

    @Test
    public void test_07_loginWithEmptyFields() {
        loginPage.authorization("", "");
        String messageActual = driver.findElement(ERROR_LOCATOR).getText();
        String messageExpected = "Your login attempt was not successful, try again.\n"
                + "Caused : Username/Password entered is incorrect.";
        assertEquals(messageExpected, messageActual);
    }

    @Test
    public void test_08_loginWithIncorrectData() {
        loginPage.authorization("noname", "noname");
        String messageActual = driver.findElement(ERROR_LOCATOR).getText();
        String messageExpected = "Your login attempt was not successful, try again.\n"
                + "Caused : Username/Password entered is incorrect.";
        assertEquals(messageExpected, messageActual);
    }

    @Test
    public void test_09_loginWithCorrectData() {
        String loginName = "Username_111";
        loginPage.authorization(loginName, "Password_111");
        String titleActual = driver.getTitle();
        String titleExpected = "Top";
        assertEquals(titleExpected, titleActual);
        
        String headerActual = driver.findElement(By.id("header")).getText();
        String headerExpected = "Software Logo";
        assertEquals(headerExpected, headerActual);
        
        String nameActual = driver.findElement(By.xpath("//*[@id='navigation_toolbar']/.//td[3]")).getText();
        assertEquals(loginName, nameActual);
    }

}
