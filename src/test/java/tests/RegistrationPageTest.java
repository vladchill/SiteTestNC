package tests;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import laba.RegistrationPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import static org.junit.Assert.*;
import org.openqa.selenium.WebElement;


public class RegistrationPageTest {
    
    private static WebDriver driver;
    private static RegistrationPage registrationPage;
    private final String MESSAGE = "//*[@id=\"content\"]/div";
    private final By MESSAGE_LOCATOR = By.xpath(MESSAGE);
    private static final String ERROR_LOGIN = ".//*[@id='registerForm:username']/parent::td/following-sibling::td/span";
    private static final String ERROR_PASSWORD = ".//*[@id='registerForm:password']/parent::td/following-sibling::td/span";
    private static final String ERROR_CONFIRM_PASSWORD = ".//*[@id='registerForm:confirmPassword']/parent::td/following-sibling::td/span";
    private static final String ERROR_EMAIL = ".//*[@id='registerForm:email']/parent::td/following-sibling::td/span";

    @Before
    public void setUp() {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        
        registrationPage = new RegistrationPage(driver);
    }
    
    @After
    public void closeBrowser() {
        if (driver != null) {
            driver.close();
            driver.quit();
        }
    }

    @Test
    public void test_01_goToRegistrationPage() {
        String titleActual = driver.getTitle();
        String titleExpected = "Registration";
        String headerActual = driver.findElement(By.id("header")).getText();
        String headerExpected = "Software Logo";
        System.out.println(titleExpected + " " + titleActual);
        assertEquals(titleExpected, titleActual);
        assertEquals(headerExpected, headerActual);
    }

    @Test
    public void test_02_registerUserWithEmptyFields() {
        registrationPage.registration("", "", "", "", "RO");
        List<WebElement> listErrorsActual = driver.findElements(By.className("error"));
        List<WebElement> listErrorsExpected = new ArrayList<WebElement>();
        listErrorsExpected.add(driver.findElement(By.xpath(ERROR_LOGIN)));
        listErrorsExpected.add(driver.findElement(By.xpath(ERROR_PASSWORD)));
        listErrorsExpected.add(driver.findElement(By.xpath(ERROR_CONFIRM_PASSWORD)));
        listErrorsExpected.add(driver.findElement(By.xpath(ERROR_EMAIL)));
        for (WebElement listAct : listErrorsActual) {
            assertTrue(listAct.isDisplayed());
        }       
        assertEquals(listErrorsExpected, listErrorsActual);
    }
    
    @Test
    public void test_03_checkValidationFields() {
        registrationPage.registration("!@#$%^&*", "Pass", "Pas", "mail", "RO");
        List<WebElement> listErrorsActual = driver.findElements(By.className("error"));
        List<WebElement> listErrorsExpected = new ArrayList<WebElement>();
        listErrorsExpected.add(driver.findElement(By.xpath(ERROR_LOGIN)));
        listErrorsExpected.add(driver.findElement(By.xpath(ERROR_PASSWORD)));
        listErrorsExpected.add(driver.findElement(By.xpath(ERROR_EMAIL)));
        for (WebElement listAct : listErrorsActual) {
            assertTrue(listAct.isDisplayed());
        }
        assertEquals(listErrorsExpected, listErrorsActual);
    }

    @Test
    public void test_04_registerNewUser() {
        String userName = "Username_111";
//        userName+=(int)(Math.random()*100);
        String userPass = "Password_111";
        String userMail = "MyE-mail.-1@gmail.com";
        String role = "RW";        
        String messageActual = "";
        String messageExpected = "You have successfully registered\n" + "Use your credentials to login";
        
        registrationPage.registration(userName, userPass, userPass, userMail, role);
        System.out.println("Username==="+userName);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        if (driver.getTitle().equals("Login Page")) {
            messageActual = driver.findElement(MESSAGE_LOCATOR).getText();
        }
        assertEquals(messageExpected, messageActual);
    }

}
