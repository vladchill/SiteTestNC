package laba;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

    private WebDriver driver;
    private By userNameLocator = By.name("j_username");
    private By userPasswordLocator = By.name("j_password");
    private By userLoginButtonLocator = By.name("submit");
    private static final String BASE_URL = "http://s1.web.sumdu.edu.ua:8080/login.jsp";

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        driver.get(BASE_URL);       
    }

    public void setUsername(String username) {
        driver.findElement(userNameLocator).sendKeys(username);
    }

    public void setPassword(String password) {
        driver.findElement(userPasswordLocator).sendKeys(password);
    }

    public void clickLoginButton() {
        driver.findElement(userLoginButtonLocator).click();
    }

    public void authorization(String username, String password) {
        setUsername(username);
        setPassword(password);
        clickLoginButton();
    }

}
