package Login;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginTest {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver-win64\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));

        try {
  
            System.out.println("🌐 Đang truy cập trang đăng nhập...");
            driver.get("http://localhost:8080/login");

   
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".login-container")));
            System.out.println("✅ Đã tải xong form đăng nhập");

          
            WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));
            emailField.sendKeys("vanc@example.com");
            System.out.println("✅ Đã nhập xong email");

            WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
            passwordField.sendKeys("password789");
            System.out.println("✅ Đã nhập xong password");


        
            WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("button.btn.btn-primary.btn-login")
            ));
            loginButton.click();
            System.out.println("🖱️ Đã click nút đăng nhập");
         
        
            try {
           
                WebElement errorMessage = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector(".alert.alert-danger")
                ));
                
                if(errorMessage.isDisplayed()) {
                    System.out.println("❌ Đăng nhập thất bại: " + errorMessage.getText());
                }
            } catch (Exception e) {
         
                wait.until(ExpectedConditions.urlContains("http://localhost:8080/"));
                System.out.println("✅ Đăng nhập thành công!");
                driver.get("http://localhost:8080/");
            }

        } catch (Exception e) {
            System.out.println("⚠️ Lỗi trong quá trình đăng nhập: " + e.getMessage());
        } finally {
     
            if (driver != null) {
                driver.quit();
                System.out.println("🛑 Đã đóng trình duyệt");
            }
        }
    }
}