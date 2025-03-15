package TestCart;

import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import static org.junit.Assert.*;

public class testRemoveProductFromCart {

    private WebDriver driver;
    private WebDriverWait wait;

    @Test
    public void testRemoveProductFromCart() {
        // Cấu hình WebDriver
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15)); // Chờ tối đa 15 giây

        try {
            loginToSystem();
            addProductToCart();
            verifyProductInCart();
            removeProductFromCart();
            verifyCartIsEmpty();

            System.out.println("✅ Kiểm thử thành công: Sản phẩm đã được xóa khỏi giỏ hàng.");

        } catch (Exception e) {
            System.out.println("❌ Lỗi xảy ra trong quá trình kiểm thử: " + e.getMessage());
            e.printStackTrace();
            fail("Test failed due to exception: " + e.getMessage());
        } finally {
            // Đóng trình duyệt
            driver.quit();
            System.out.println("Đã đóng trình duyệt.");
        }
    }

    private void loginToSystem() {
        System.out.println("Bước 1: Đăng nhập vào hệ thống...");
        driver.get("http://localhost:8080/login");

        // Kiểm tra trang có phải trang login không
        if (!driver.getCurrentUrl().contains("login")) {
            System.out.println("❌ Lỗi: Không vào được trang đăng nhập!");
            return;
        }

        // Nhập email
        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));
        emailField.sendKeys("vanc@example.com");
        System.out.println("Đã nhập email.");

        // Nhập mật khẩu
        WebElement passField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
        passField.sendKeys("password789");
        System.out.println("Đã nhập mật khẩu.");

        // Nhấn nút đăng nhập
        WebElement loginButton = driver.findElement(By.cssSelector("button.btn-login"));
        loginButton.click();
        System.out.println("Đã nhấn nút đăng nhập.");

        // Kiểm tra đăng nhập thành công
        boolean isLoggedIn = wait.until(ExpectedConditions.urlContains("/"));
        if (isLoggedIn) {
            System.out.println("✅ Đăng nhập thành công!");
        } else {
            System.out.println("❌ Đăng nhập thất bại!");
            fail("Không đăng nhập được vào hệ thống.");
        }
    }

    private void addProductToCart() {
        System.out.println("Bước 2: Mở trang sản phẩm và thêm vào giỏ hàng...");
        driver.get("http://localhost:8080/products");

        // Chờ và nhấn vào sản phẩm để mở modal chi tiết
        WebElement productCard = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".product-card")));
        productCard.click();
        System.out.println("Đã chọn sản phẩm.");

        // Chờ modal chi tiết hiển thị
        WebElement productModal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("productModal")));
        System.out.println("Modal chi tiết sản phẩm đã hiển thị.");

        // Chờ và nhấn nút "Thêm vào giỏ hàng"
        WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".btn-add-cart")));
        addToCartButton.click();
        System.out.println("Đã thêm sản phẩm vào giỏ hàng.");

    }

    private void verifyProductInCart() {
        System.out.println("Bước 3: Mở giỏ hàng và kiểm tra sản phẩm...");
        driver.get("http://localhost:8080/cart");

        // Kiểm tra sản phẩm có trong giỏ hàng không
        WebElement productInCart = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".table tbody tr")));
        assertTrue(productInCart.isDisplayed());
        System.out.println("✅ Sản phẩm đã có trong giỏ hàng.");
    }

    private void removeProductFromCart() {
        System.out.println("Bước 4: Xóa sản phẩm khỏi giỏ hàng...");
        
        // Tìm và nhấn nút xóa
        WebElement deleteButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".btn-delete")));
        deleteButton.click();
        System.out.println("Đã nhấn nút 'Xóa'");

        // Chờ sản phẩm biến mất khỏi giỏ hàng
        boolean isProductRemoved = wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".table tbody tr")));
        
        if (isProductRemoved) {
            System.out.println("✅ Sản phẩm đã bị xóa khỏi giỏ hàng.");
        } else {
            System.out.println("❌ Lỗi: Sản phẩm vẫn còn trong giỏ hàng.");
            fail("Sản phẩm không bị xóa.");
        }
    }


    private void verifyCartIsEmpty() {
        System.out.println("Bước 5: Kiểm tra giỏ hàng trống...");

        // Kiểm tra xem có dòng sản phẩm nào không
        boolean isCartEmpty = wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".table tbody tr")));

        // Hoặc kiểm tra có hiển thị thông báo "Giỏ hàng trống" không
        try {
            WebElement emptyCartMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".empty-cart")));
            assertTrue(emptyCartMessage.isDisplayed());
            System.out.println("✅ Giỏ hàng đã trống.");
        } catch (TimeoutException e) {
            if (!isCartEmpty) {
                fail("Giỏ hàng không trống sau khi xóa sản phẩm.");
            }
        }
    }
}
