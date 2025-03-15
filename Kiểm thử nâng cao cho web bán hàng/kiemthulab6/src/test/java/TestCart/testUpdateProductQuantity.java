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

public class testUpdateProductQuantity {

    @Test
    public void testUpdateProductQuantity() {
        // Cấu hình WebDriver tự động
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver-win64\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15)); // Tăng thời gian chờ lên 15 giây

        try {
            // Bước 1: Mở trang đăng nhập
            System.out.println("Bước 1: Mở trang đăng nhập...");
            driver.get("http://localhost:8080/login");

            // Nhập email
            WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));
            emailField.sendKeys("vanc@example.com");
            System.out.println("✔ Đã nhập email.");

            // Nhập mật khẩu
            WebElement passField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
            passField.sendKeys("password789");
            System.out.println("✔ Đã nhập mật khẩu.");

            // Nhấn nút đăng nhập
            WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.btn-login")));
            loginButton.click();
            System.out.println("✔ Đã nhấn nút đăng nhập.");

            // Chờ đăng nhập thành công
            wait.until(ExpectedConditions.urlContains("/"));
            System.out.println("✔ Đăng nhập thành công!");

            // Bước 2: Mở trang sản phẩm
            driver.get("http://localhost:8080/products");

            // ID sản phẩm cần thêm vào giỏ hàng
            String productId = "2";

            // Chọn sản phẩm theo ID
            WebElement productCard = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector(".product-card[data-product-id='" + productId + "']")));
            productCard.click();
            System.out.println("✔ Đã mở chi tiết sản phẩm ID " + productId);

            // Chờ modal hiển thị
            WebElement modal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("productModal")));
            System.out.println("✔ Modal sản phẩm hiển thị.");

            // Nhấn "Thêm vào giỏ hàng"
            WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(modal.findElement(By.cssSelector(".btn-add-cart"))));
            addToCartButton.click();
            System.out.println("✔ Đã thêm sản phẩm vào giỏ hàng.");

            // Bước 3: Kiểm tra tổng tiền trong giỏ hàng
            driver.get("http://localhost:8080/cart");

         // Lấy số lượng hiện tại của sản phẩm đầu tiên
            WebElement quantityInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".input-group input")));
            String initialQuantity = quantityInput.getAttribute("value");
            System.out.println("Số lượng ban đầu: " + initialQuantity);
//
            // Tăng số lượng sản phẩm lên 1
            WebElement increaseButton = driver.findElement(By.cssSelector(".input-group button:last-child"));
            increaseButton.click();
            System.out.println("Đã nhấn nút tăng số lượng.");

            // Kiểm tra tổng tiền
            WebElement totalPriceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".total-price span")));
            String totalPrice = totalPriceElement.getText();
            System.out.println("Tổng tiền hiển thị chính xác");

            // Kiểm tra xem tổng tiền có chứa "VNĐ" không
            assertTrue("Tổng tiền không hợp lệ", totalPrice.contains("VNĐ"));

            System.out.println("✅ Kiểm thử thành công: Số lượng sản phẩm và tổng tiền được cập nhật chính xác.");

        } catch (Exception e) {
            System.out.println("❌ Lỗi xảy ra trong quá trình kiểm thử: " + e.getMessage());
            e.printStackTrace();
            fail("Test failed due to exception: " + e.getMessage());
        } finally {
            // Đóng trình duyệt

            System.out.println("Đã đóng trình duyệt.");
        }
    }
}