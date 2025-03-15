package CRUD;

import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import static org.junit.Assert.*;

public class testAddNewProduct {

    final String TEST_IMAGE_PATH = "C:\\Java5\\ass\\src\\main\\resources\\static\\img\\logotrungthu.jpg";
    final String PRODUCT_NAME = "Product Test " + System.currentTimeMillis();
    final String PRODUCT_PRICE = "500000";
    final String PRODUCT_DESCRIPTION = "Mô tả sản phẩm test tự động";

    @Test
    public void testAddNewProduct() {
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver-win64\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        try {

            // Bước 2: Điều hướng đến trang thêm sản phẩm
            System.out.println("🟢 Bước 2: Điều hướng đến trang thêm sản phẩm...");
            driver.get("http://localhost:8080/admin/add-product");
            System.out.println("✔ Đã vào trang thêm sản phẩm.");

            // Bước 3: Điền thông tin sản phẩm
            System.out.println("🟢 Bước 3: Nhập thông tin sản phẩm...");
            WebElement nameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("nameproduct")));
            scrollToElement(driver, nameField);
            nameField.sendKeys(PRODUCT_NAME);
            System.out.println("✔ Nhập tên sản phẩm: " + PRODUCT_NAME);

            WebElement priceField = driver.findElement(By.name("price"));
            scrollToElement(driver, priceField);
            priceField.sendKeys(PRODUCT_PRICE);
            System.out.println("✔ Nhập giá sản phẩm: " + PRODUCT_PRICE);

            WebElement descriptionField = driver.findElement(By.name("description"));
            scrollToElement(driver, descriptionField);
            descriptionField.sendKeys(PRODUCT_DESCRIPTION);
            System.out.println("✔ Nhập mô tả sản phẩm: " + PRODUCT_DESCRIPTION);

            WebElement fileInput = driver.findElement(By.name("photoFile"));
            fileInput.sendKeys(TEST_IMAGE_PATH);
            System.out.println("✔ Tải lên hình ảnh sản phẩm: " + TEST_IMAGE_PATH);

            // Bước 4: Submit form
            System.out.println("🟢 Bước 4: Gửi form thêm sản phẩm...");
            WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(),'Lưu thay đổi')]")
            ));
            scrollToElement(driver, submitButton);
            submitButton.click();
            System.out.println("✔ Nhấn nút 'Lưu thay đổi'.");

            // Bước 5: Kiểm tra kết quả
            System.out.println("🟢 Bước 5: Kiểm tra kết quả sau khi thêm sản phẩm...");
            wait.until(ExpectedConditions.urlContains("/adminproducts"));
            System.out.println("✔ Điều hướng thành công đến trang danh sách sản phẩm.");

            // Kiểm tra thông báo thành công
            WebElement successAlert = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class,'alert-success') and contains(.,'thành công')]")
            ));
            System.out.println("🔔 Thông báo hệ thống: " + successAlert.getText());

            // Kiểm tra sản phẩm trong danh sách
            WebElement newProduct = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h5[contains(text(),'" + PRODUCT_NAME + "')]")
            ));
            assertNotNull("❌ Không tìm thấy sản phẩm trong danh sách!", newProduct);
            System.out.println("✅ Thêm sản phẩm thành công!");

        } catch (Exception e) {
            System.err.println("❌ Lỗi: " + e.getMessage());
            e.printStackTrace();
            fail("Test thất bại do ngoại lệ: " + e.getMessage());
        } finally {
            driver.quit();
            System.out.println("🚪 Đã đóng trình duyệt.");
        }
    }

    private void scrollToElement(WebDriver driver, WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
            "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", 
            element
        );
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
