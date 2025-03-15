package CRUD;

import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import static org.junit.Assert.*;

public class testremoveEmployee {

    @Test
    public void testDeleteEmployeeByEmail() {
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver-win64\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        try {
            // Truy cập trang quản lý nhân viên
            driver.get("http://localhost:8080/adminusers");
            System.out.println("🟢 Đã truy cập trang quản lý nhân viên.");

            // Tìm nhân viên theo email
            String targetEmail = "nhantranvan@example.com"; // Thay email cần xóa
            System.out.println("🔍 Đang tìm nhân viên có email: " + targetEmail + "...");
            WebElement row = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//td[normalize-space()='" + targetEmail + "']/.."))); // Tìm theo email
            System.out.println("✅ Đã tìm thấy nhân viên cần xóa.");
            
            // Click nút Xóa
            System.out.println("⚠ Đang thực hiện thao tác xóa...");
            WebElement deleteButton = row.findElement(
                By.xpath(".//a[contains(@class, 'btn-danger')]"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", deleteButton);
            System.out.println("✅ Đã click vào nút xóa nhân viên.");

            System.out.println("✅ Đã xác nhận xóa nhân viên.");

            System.out.println("🎉 Xóa nhân viên thành công!");

        } catch (Exception e) {
            System.err.println("❌ Lỗi: " + e.getMessage());
            e.printStackTrace();
            fail("Lỗi: " + e.getMessage());
        } finally {
            driver.quit();
        }
    }
}
