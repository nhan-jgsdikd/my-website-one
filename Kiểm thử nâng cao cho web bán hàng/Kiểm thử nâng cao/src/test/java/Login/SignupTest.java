package Login;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class SignupTest {
    public static void main(String[] args) {
      
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver-win64\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        try {
   
            System.out.println("🟡 Đang mở trang đăng ký...");
            driver.get("http://localhost:8080/signup");

            // Đợi tối đa 10 giây để phần tử xuất hiện
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Nhập tên đầy đủ
            WebElement nameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name")));
            nameField.sendKeys("Nguyen Van Phuc");
            System.out.println("📝 Đã nhập tên đầy đủ.");

            // Nhập email
            WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));
            emailField.sendKeys("phuc@gmail.com");
            System.out.println("📩 Đã nhập email.");

            // Nhập mật khẩu
            WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
            passwordField.sendKeys("123456");
            System.out.println("🔑 Đã nhập mật khẩu.");

            // Nhập lại mật khẩu
            WebElement confirmPasswordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("confirmPassword")));
            confirmPasswordField.sendKeys("123456");
            System.out.println("✅ Đã xác nhận mật khẩu.");

            // Nhấn nút đăng ký
            WebElement signupButton = driver.findElement(By.cssSelector("button.btn-signup"));
            signupButton.click();
            System.out.println("🔘 Đã nhấn nút đăng ký.");

            // Đợi phản hồi (thông báo lỗi hoặc chuyển hướng)
            boolean isErrorDisplayed = wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.alert-danger")), // Kiểm tra thông báo lỗi
                ExpectedConditions.urlContains("login") // Kiểm tra nếu đăng ký thành công và chuyển hướng
            ));

            // Kiểm tra kết quả
            if (driver.findElements(By.cssSelector("div.alert-danger")).size() > 0) {
                System.out.println("❌ Đăng ký thất bại: " + driver.findElement(By.cssSelector("div.alert-danger")).getText());
            } else {
                System.out.println("🎉 Đăng ký thành công! Chuyển hướng đến trang đăng nhập...");
            }
        } catch (Exception e) {
            System.out.println("⚠️ Lỗi: " + e.getMessage());
        } finally {
            // Đóng trình duyệt
            if (driver != null) {
                driver.quit();
                System.out.println("🔚 Đã đóng trình duyệt.");
            }
        }
    }
}
