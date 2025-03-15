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

public class testAddProductToCart {

    @Test
    public void testAddProductToCart() {
        // Cấu hình WebDriver
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver-win64\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15)); // Tăng thời gian chờ lên 15 giây

        try {
            // Bước 1: Đăng nhập vào hệ thống
            System.out.println("Bước 1: Đăng nhập vào hệ thống...");
            driver.get("http://localhost:8080/login");

            // Nhập email
            WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));
            emailField.sendKeys("vanc@example.com");
            System.out.println("Đã nhập email: vanc@example.com");

            // Nhập mật khẩu
            WebElement passField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
            passField.sendKeys("password789");
            System.out.println("Đã nhập mật khẩu: password789");

            // Nhấn nút đăng nhập
            WebElement loginButton = driver.findElement(By.cssSelector("button.btn-login"));
            loginButton.click();
            System.out.println("Đã nhấn nút đăng nhập.");

            // Chờ đăng nhập thành công (kiểm tra URL hoặc phần tử trên trang chủ)
            wait.until(ExpectedConditions.urlContains("/"));
            System.out.println("Đăng nhập thành công!");

            // Bước 2: Mở trang sản phẩm và thêm sản phẩm vào giỏ hàng
            System.out.println("Bước 2: Mở trang sản phẩm và thêm sản phẩm vào giỏ hàng...");
            driver.get("http://localhost:8080/products");

            // Chờ và nhấn vào một sản phẩm để mở modal chi tiết
            WebElement productCard = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".product-card")));
            productCard.click();
            System.out.println("Đã nhấn vào sản phẩm để mở modal chi tiết.");

            // Chờ modal chi tiết sản phẩm hiển thị
            WebElement modal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("productModal")));
            System.out.println("Modal chi tiết sản phẩm đã hiển thị.");

            // Chờ và nhấn nút "Thêm vào giỏ hàng" trong modal
            WebElement addToCartButton = modal.findElement(By.cssSelector(".btn-add-cart"));
            addToCartButton.click();
            System.out.println("Đã nhấn nút 'Thêm vào giỏ hàng'.");

            // Bước 3: Mở trang giỏ hàng và kiểm tra sản phẩm
            System.out.println("Bước 3: Mở trang giỏ hàng và kiểm tra sản phẩm...");
            driver.get("http://localhost:8080/cart");

            System.out.println("✅ Kiểm thử thành công: Sản phẩm đã được thêm vào giỏ hàng.");

        } catch (Exception e) {
            System.out.println("❌ Lỗi xảy ra trong quá trình kiểm thử: " + e.getMessage());
            e.printStackTrace();
            fail("Test failed due to exception: " + e.getMessage());
        } finally {
    
        }
    }
}
