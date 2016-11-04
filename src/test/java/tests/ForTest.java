package tests;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import ru.yandex.qatools.allure.annotations.Attachment;
import static org.junit.Assert.*;


public class ForTest {

    public static WebDriver driver;
    
    public void saveSreenshot() {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            String title = driver.getTitle().replaceAll(" \\|", "");            
            FileUtils.copyFile(scrFile, new File( ".\\target\\screenshots\\"+ title + ".jpg" ));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Attachment(value = "Page screenshot", type = "image/png")
    protected byte[] saveScreenshotAllure() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    @BeforeClass
    public static void openDriver() {
        driver = new FirefoxDriver();
        driver.get("https://google.com.ua");
    }
    
    @AfterClass
    public static void closeDriver() {
        if (driver != null) {
            driver.close();
            driver.quit();
        }
    }
    
    @Before
    public void test1() {
        WebElement searchField = driver.findElement(By.cssSelector("input#lst-ib.gsfi"));
        searchField.clear();
        searchField.sendKeys("NetCracker Su");
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);               
       
        List<WebElement> findElements1 = driver.findElements(By.xpath("//*/ul[@class='sbsb_b']/li"));
        for (int i = 0; i < findElements1.size(); i++) {
            if (findElements1.get(i).getText().equalsIgnoreCase("NetCracker Sumy")) { //NetCracker сумы
//                driver.findElement(By.linkText(findElements1.get(i).getText())).click();
                driver.findElement(By.xpath("//*/ul[@class='sbsb_b']/li[" + (i+1) + "]/div")).click();                
                break;
            } else if (i == findElements1.size() - 1) {                
                searchField.clear();
                searchField.sendKeys("NetCracker Sumy");
                searchField.submit();
            }
        }
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        List<WebElement> findElements2 = driver.findElements(By.xpath("//*/div[@class='srg']/div/*/h3"));
        for (int i = 0; i < findElements2.size(); i++) {
            if (findElements2.get(i).getText().equalsIgnoreCase("NetCracker. :: Суми")) {
                driver.findElement(By.xpath("//*/div[@class='srg']/div[" + (i+1) + "]/*/h3")).click();
                break;
            }
            else if (i == findElements2.size() - 1 ) {
                driver.get("http://www.netcracker.com/ukr/vacancies");                
            }
        }
        
        driver.findElement(By.cssSelector("div > article:nth-child(3) > p > a")).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        
        saveSreenshot();
        saveScreenshotAllure();  
    }
    
    @Test
    public void testVacancies() {
        HashMap<String, String> vacancies1 = new HashMap<String, String>();
        List<WebElement> findElements3 = driver.findElements(By.xpath("//*/div/article/h4"));

        for (int i = 0; i < findElements3.size(); i++) {
            List<WebElement> findElements4 = driver.findElements(By.xpath("//*/div/article[" + (i+1) + "]/ul/li/h4/a"));

            for (int j = 0; j < findElements4.size(); j++) {                
                vacancies1.put(findElements4.get(j).getText(), findElements3.get(i).getText());
            }
        }
        
        HashMap<String, String> vacanciesExpected = new HashMap<String, String>();
        vacanciesExpected.put("Technical Writer", "Knowledge Management and Research");
        vacanciesExpected.put("Help Desk Specialist", "IT Department");
        vacanciesExpected.put("Technical Solution Support Engineer", "Technical Solution Support");
        vacanciesExpected.put("QA Engineer", "QA");
        vacanciesExpected.put("QA/TA Engineer", "QA");
        vacanciesExpected.put("OSG Specialist", "Operations");
        vacanciesExpected.put("Junior QA Engineer", "QA");
        
        Iterator it = vacancies1.entrySet().iterator();
        
        for (Map.Entry<String, String> entryExp : vacanciesExpected.entrySet()) {
            Entry entryAct = (Entry) it.next();
            
            System.out.println(entryExp.getKey()+"===="+entryAct.getKey());
            assertEquals(entryExp.getKey(), entryAct.getKey());
            assertEquals(entryExp.getValue(), entryAct.getValue());
        }
    }
    

}
