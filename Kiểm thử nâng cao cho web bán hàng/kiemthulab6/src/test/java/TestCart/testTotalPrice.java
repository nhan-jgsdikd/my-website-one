package TestCart;

import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.Assert.*;

public class testTotalPrice {

    @Test
    public void testTotalPrice() {
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver-win64\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        try {
            System.out.println("🔹 Bắt đầu kiểm thử tổng tiền trong giỏ hàng...");
            
            // Bước 1: Đăng nhập
            System.out.println("➡ Mở trang đăng nhập...");
            driver.get("http://localhost:8080/login");

            WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));
            emailField.sendKeys("vanc@example.com");

            WebElement passField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
            passField.sendKeys("password789");

            WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.btn-login")));
            loginButton.click();

            wait.until(ExpectedConditions.urlContains("/"));
            System.out.println("✔ Đăng nhập thành công!");

            // Bước 2: Mở trang sản phẩm và thêm vào giỏ hàng
            System.out.println("➡ Điều hướng đến trang sản phẩm...");
            driver.get("http://localhost:8080/products");

            // Đợi danh sách sản phẩm load xong
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".product-card")));
            System.out.println("✔ Danh sách sản phẩm đã tải xong!");

            String productId = "10";
            WebElement productCard = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector(".product-card[data-product-id='" + productId + "']")));
            
            // Scroll đến phần tử trước khi click
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", productCard);
            Thread.sleep(500); // Đợi một chút sau khi scroll
            productCard.click();
            System.out.println("✔ Đã mở chi tiết sản phẩm (ID: " + productId + ")");

            // Xử lý modal
            WebElement modal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("productModal")));
            WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(modal.findElement(By.cssSelector(".btn-add-cart"))));
            addToCartButton.click();
            System.out.println("✔ Sản phẩm đã được thêm vào giỏ hàng!");

            // Bước 3: Kiểm tra tổng tiền
            System.out.println("➡ Chuyển đến giỏ hàng để kiểm tra tổng tiền...");
            driver.get("http://localhost:8080/cart");

            WebElement totalPriceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".total-price span")));
            String totalPrice = totalPriceElement.getText();
            System.out.println("🛒 Tổng tiền hiển thị: " + totalPrice);

            assertTrue("❌ Tổng tiền không hợp lệ!", totalPrice.contains("VNĐ"));
            System.out.println("✅ Kiểm tra tổng tiền thành công!");

        } catch (Exception e) {
            System.err.println("❌ Lỗi: " + e.getMessage());
            fail("Test thất bại: " + e.getMessage());
        } finally {
            System.out.println("🔹 Đóng trình duyệt...");
            driver.quit();
            System.out.println("✅ Kiểm thử hoàn thành!");
        }
    }
}
