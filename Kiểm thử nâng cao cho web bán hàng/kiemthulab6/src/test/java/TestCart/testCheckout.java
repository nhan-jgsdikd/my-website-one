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

public class testCheckout {

    @Test
    public void testCheckout() {
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
            WebElement productModal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("productModal")));
            System.out.println("Modal chi tiết sản phẩm đã hiển thị.");

            // Chờ và nhấn nút "Thêm vào giỏ hàng" trong modal
            WebElement addToCartButton = productModal.findElement(By.cssSelector(".btn-add-cart"));
            addToCartButton.click();
            System.out.println("Đã nhấn nút 'Thêm vào giỏ hàng'.");

            // Bước 3: Mở trang giỏ hàng và kiểm tra sản phẩm
            System.out.println("Bước 3: Mở trang giỏ hàng và kiểm tra sản phẩm...");
            driver.get("http://localhost:8080/cart");

            // Kiểm tra xem sản phẩm đã được thêm vào giỏ hàng
            WebElement productInCart = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".table tbody tr")));
            assertTrue(productInCart.isDisplayed());
            System.out.println("Sản phẩm đã được thêm vào giỏ hàng.");

         // Bước 4: Thanh toán sản phẩm trong giỏ hàng
            System.out.println("Bước 4: Thanh toán sản phẩm trong giỏ hàng...");

            // Chờ và nhấn nút "Thanh toán" để mở modal thanh toán
            WebElement checkoutButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.btn-success[data-bs-target='#checkoutModal']")));
            checkoutButton.click();
            System.out.println("Đã nhấn nút 'Thanh toán'.");

            // Chờ modal thanh toán hiển thị
            WebElement checkoutModal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("checkoutModal")));
            System.out.println("Modal thanh toán đã hiển thị.");

            // Chờ phần tử #fullName hiển thị trong modal
            WebElement fullNameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fullName")));
            fullNameField.sendKeys("Nguyễn Văn A");
            System.out.println("Đã nhập họ tên: Nguyễn Văn A");

            WebElement phoneField = checkoutModal.findElement(By.id("phone"));
            phoneField.sendKeys("0123456789");
            System.out.println("Đã nhập số điện thoại: 0123456789");

            WebElement emailField1 = checkoutModal.findElement(By.id("email"));
            emailField1.sendKeys("nguyenvana@example.com");
            System.out.println("Đã nhập email: nguyenvana@example.com");

            WebElement addressField = checkoutModal.findElement(By.id("address"));
            addressField.sendKeys("123 Đường ABC, Quận 1, TP.HCM");
            System.out.println("Đã nhập địa chỉ: 123 Đường ABC, Quận 1, TP.HCM");

            WebElement cityField = checkoutModal.findElement(By.id("city"));
            cityField.sendKeys("Hồ Chí Minh");
            System.out.println("Đã nhập thành phố: Hồ Chí Minh");

            WebElement districtField = checkoutModal.findElement(By.id("district"));
            districtField.sendKeys("Quận 1");
            System.out.println("Đã nhập quận/huyện: Quận 1");

            // Chọn phương thức thanh toán (COD)
            WebElement codPaymentMethod = checkoutModal.findElement(By.id("cod"));
            codPaymentMethod.click();
            System.out.println("Đã chọn phương thức thanh toán COD.");

            // Nhấn nút "Xác nhận thanh toán"
            WebElement confirmButton = checkoutModal.findElement(By.xpath("//button[contains(text(), 'Xác nhận thanh toán')]"));
            confirmButton.click();
            System.out.println("Đã nhấn nút 'Xác nhận thanh toán'.");

            System.out.println("Thông báo thanh toán thành công: ");

        } catch (Exception e) {
            System.out.println("❌ Lỗi xảy ra trong quá trình kiểm thử: " + e.getMessage());
            e.printStackTrace();
            fail("Test failed due to exception: " + e.getMessage());
        } finally {

        }
    }
}