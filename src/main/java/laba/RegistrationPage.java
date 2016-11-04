package laba;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;


public class RegistrationPage {

    private WebDriver driver;
    private static final String BASE_URL = "http://s1.web.sumdu.edu.ua:8080/login.jsp";
    private static By registrationButtonLocator = By.linkText("Registration");
    private By formRegistrationButtonLocator = By.name("registerForm:j_idt25");
    private By formRegistrationUsername = By.id("registerForm:username");
    private By formRegistrationPassword = By.id("registerForm:password");
    private By formRegistrationConfirmPassword = By.id("registerForm:confirmPassword");
    private By formRegistrationEmail = By.id("registerForm:email");
    private By roleLocator = By.id("registerForm:role");

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
        driver.get(BASE_URL);
        driver.findElement(registrationButtonLocator).click();
    }

    public void setUsername(String username) {
        driver.findElement(formRegistrationUsername).sendKeys(username);
    }

    public void setPassword(String password) {
        driver.findElement(formRegistrationPassword).sendKeys(password);
    }

    public void setConfirmPassword(String confirmPassword) {
        driver.findElement(formRegistrationConfirmPassword).sendKeys(confirmPassword);
    }

    public void setEmail(String email) {
        driver.findElement(formRegistrationEmail).sendKeys(email);
    }

    public void setRole(String role) {
        Select selector = new Select(driver.findElement(roleLocator));
        selector.selectByValue(role);
    }

    public void clickRegistrationButton() {
        driver.findElement(formRegistrationButtonLocator).click();

    }

    public void registration(String username, String password, String confirmPassword, String email, String role) {
        setUsername(username);
        setPassword(password);
        setConfirmPassword(confirmPassword);
        setEmail(email);
        setRole(role);
        clickRegistrationButton();

    }
}
