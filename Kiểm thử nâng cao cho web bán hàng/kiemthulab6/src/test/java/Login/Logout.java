package Login;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class Logout {
    public static void main(String[] args) {
        // Cấu hình tự động tải ChromeDriver
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver-win64\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        try {
      
            System.out.println("🟡 Đang mở trang đăng nhập...");
            driver.get("http://localhost:8080/login");

   
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            
   
            WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));
            emailField.sendKeys("vanc@example.com");
            System.out.println("📩 Đã nhập email.");

  
            WebElement passField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
            passField.sendKeys("password789");
            System.out.println("🔑 Đã nhập mật khẩu.");

            
            WebElement loginButton = driver.findElement(By.cssSelector("button.btn-login"));
            loginButton.click();
            System.out.println("🔘 Đã nhấn nút đăng nhập.");

          
            boolean isErrorDisplayed = wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.alert-danger")),
                ExpectedConditions.urlContains("/") 
            ));


            if (driver.findElements(By.cssSelector("div.alert-danger")).size() > 0) {
                System.out.println("❌ Đăng nhập thất bại: Sai email hoặc mật khẩu!");
                return; 
            } else {
                System.out.println("✅ Đăng nhập thành công!");
            }

     
            driver.get("http://localhost:8080/");
            System.out.println("🌍 Đã điều hướng đến trang chính.");

           
            WebElement dropdownMenu = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector(".nav-item.dropdown .dropdown-toggle")));
            dropdownMenu.click();
            System.out.println("✔ Đã mở menu người dùng.");

        
            WebElement logoutButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector(".dropdown-menu .dropdown-item[href*='/logout']")));
            logoutButton.click();
            System.out.println("✔ Đã click nút logout.");

    
            wait.until(ExpectedConditions.urlContains("/login"));
            System.out.println("✅ Đăng xuất thành công! Trở về trang đăng nhập.");

        } catch (Exception e) {
            System.out.println("⚠️ Lỗi: " + e.getMessage());
        } finally {
        
            if (driver != null) {
                driver.quit();
                System.out.println("🔚 Đã đóng trình duyệt.");
            }
        }
    }
}
