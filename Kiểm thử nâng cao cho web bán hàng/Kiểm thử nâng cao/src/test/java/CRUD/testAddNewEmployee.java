package CRUD;

import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import static org.junit.Assert.*;

public class testAddNewEmployee {

    @Test
    public void testAddNewEmployee() {
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver-win64\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        try {
          
            //Mở trang thêm người dùng
            driver.get("http://localhost:8080/adminusers");
            System.out.println("Điều hướng đến trang quản lý người dùng.");

            // Mở form thêm người dùng
            WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[contains(text(),'Thêm người dùng')]")));
            addButton.click();
            System.out.println("✔ Đã mở form thêm người dùng.");

            // Điền thông tin form
            WebElement form = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".card")));

            // Điền username
            WebElement usernameField = form.findElement(By.cssSelector("input[placeholder='Nhập tên người dùng']"));
            usernameField.sendKeys("tester_selenium");
            System.out.println("✔ Đã nhập tên người dùng: tester_selenium");

            // Điền email
            WebElement emailInput = form.findElement(By.cssSelector("input[placeholder='example@email.com']"));
            emailInput.sendKeys("employee-to-remove@example.com");
            System.out.println("✔ Đã nhập email: employee-to-remove@example.com");

            // Điền password
            WebElement passwordInput = form.findElement(By.cssSelector("input[type='password']"));
            passwordInput.sendKeys("Test@123");
            System.out.println("✔ Đã nhập mật khẩu: Test@123");

            // Chọn vai trò
            WebElement roleSelect = form.findElement(By.cssSelector("select.form-select"));
            new Select(roleSelect).selectByValue("ADMIN");
            System.out.println("✔ Đã chọn vai trò: ADMIN");

            // Submit form
            WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector("button.btn-primary[type='submit']")));

            try {
                submitButton.click();
                System.out.println("✔ Đã nhấn nút Submit.");
            } catch (ElementClickInterceptedException e) {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].click();", submitButton);
                System.out.println("✔ Đã submit form bằng JavaScript.");
            }

            // Bước 5: Kiểm tra thông báo thành công
            WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".alert-success")));
            String messageText = successMessage.getText();
            System.out.println("🔔 Thông báo từ hệ thống: " + messageText);

            // Kiểm tra nội dung thông báo
            assertTrue("❌ Thông báo không hợp lệ!", messageText.contains("Thêm người dùng thành công"));
            System.out.println("✅ Thêm người dùng thành công!");

        } catch (Exception e) {
            System.out.println("❌ Lỗi xảy ra: " + e.getMessage());
            e.printStackTrace();
            fail("Test failed due to exception: " + e.getMessage());
        } finally {
            // Đóng trình duyệt
            driver.quit();
            System.out.println("🚪 Đã đóng trình duyệt.");
        }
    }
}
