package TestCart;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.Assert.*;

public class CartTest {
    @Test
    public void testEmptyCart() {
        // Cấu hình đường dẫn đến ChromeDriver
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver-win64\\chromedriver.exe");

        // Khởi tạo WebDriver
        WebDriver driver = new ChromeDriver();

        try {
            // Bước 1: Mở trang giỏ hàng
            System.out.println("Bước 1: Mở trang giỏ hàng...");
            driver.get("http://localhost:8080/cart");

            // Sử dụng WebDriverWait để chờ phần tử xuất hiện
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Bước 2: Kiểm tra thông báo giỏ hàng trống
            System.out.println("Bước 2: Kiểm tra thông báo giỏ hàng trống...");
            WebElement emptyCartMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("empty-cart")));
            assertTrue("Thông báo giỏ hàng trống không hiển thị!", emptyCartMessage.isDisplayed());
            System.out.println("✔ Thông báo giỏ hàng trống đã hiển thị.");

            // Bước 3: Kiểm tra tiêu đề của giỏ hàng trống
            System.out.println("Bước 3: Kiểm tra tiêu đề của giỏ hàng trống...");
            WebElement emptyCartTitle = emptyCartMessage.findElement(By.tagName("h1"));
            assertEquals("Tiêu đề giỏ hàng trống không đúng!", "Giỏ hàng của bạn", emptyCartTitle.getText());
            System.out.println("✔ Tiêu đề giỏ hàng trống đúng: 'Giỏ hàng của bạn'.");

            // Bước 4: Kiểm tra nội dung thông báo
            System.out.println("Bước 4: Kiểm tra nội dung thông báo...");
            WebElement emptyCartText = emptyCartMessage.findElement(By.tagName("p"));
            assertEquals("Nội dung thông báo không đúng!", "Giỏ hàng của bạn đang trống.", emptyCartText.getText());
            System.out.println("✔ Nội dung thông báo đúng: 'Giỏ hàng của bạn đang trống.'.");

            // Bước 5: Kiểm tra nút 'Tiếp tục mua sắm'
            System.out.println("Bước 5: Kiểm tra nút 'Tiếp tục mua sắm'...");
            WebElement continueShoppingButton = emptyCartMessage.findElement(By.tagName("a"));
            assertEquals("Văn bản nút không đúng!", "Tiếp tục mua sắm", continueShoppingButton.getText());
            System.out.println("✔ Văn bản nút đúng: 'Tiếp tục mua sắm'.");

            // Bước 6: Kiểm tra href của nút 'Tiếp tục mua sắm'
            System.out.println("Bước 6: Kiểm tra href của nút 'Tiếp tục mua sắm'...");
            String fullUrl = continueShoppingButton.getAttribute("href");
            String relativePath = fullUrl.substring(fullUrl.indexOf("/", 7)); // Cắt từ sau "http://"
            assertEquals("Đường dẫn không đúng!", "/products", relativePath);
            System.out.println("✔ Đường dẫn của nút đúng: '/products'.");

            System.out.println("✅ Kiểm thử thành công: Giỏ hàng trống hiển thị đúng thông báo và nút chức năng.");

        } catch (Exception e) {
            // Xử lý ngoại lệ và in thông báo lỗi
            System.out.println("❌ Lỗi xảy ra trong quá trình kiểm thử: " + e.getMessage());
            e.printStackTrace();
            fail("Test failed due to exception: " + e.getMessage());
        } finally {

        }
    }
}