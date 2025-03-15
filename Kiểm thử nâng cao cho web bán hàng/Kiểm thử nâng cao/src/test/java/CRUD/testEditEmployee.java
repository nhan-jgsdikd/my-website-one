package CRUD;

import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import javax.swing.JOptionPane;
import java.time.Duration;
import static org.junit.Assert.*;

public class testEditEmployee {

    final String TARGET_PRODUCT_ID = "23";
    final String NEW_PRODUCT_NAME = "Test edit pdroduct ";
    final String NEW_PRODUCT_PRICE = "32990000";
    final String NEW_PRODUCT_DESCRIPTION = "Phiên bản cập nhật mới";
    final String NEW_IMAGE_PATH = "F:\\file rác\\473019006_122111619392659356_2210646491212739563_n.jpg"; // Đường dẫn ảnh mới

    @Test
    public void testUpdateProductWithId23() {
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver-win64\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));

        try {
            System.out.println("🔹 Bắt đầu test cập nhật sản phẩm...");
            
            // Truy cập trang quản lý sản phẩm
            driver.get("http://localhost:8080/adminusers");
            System.out.println("🔹 Điều hướng đến trang quản lý sản phẩm...");

            // Tìm và click nút Chỉnh sửa cho product ID
            WebElement editButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(@href,'/admin/edit-product/" + TARGET_PRODUCT_ID + "')]")
            ));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", editButton);
            System.out.println("🔹 Mở trang chỉnh sửa cho sản phẩm ID: " + TARGET_PRODUCT_ID);

            // Chờ form tải xong
            wait.until(ExpectedConditions.urlContains("/edit-product"));

            // Điền thông tin mới
            System.out.println("🔹 Điền thông tin mới...");
            
            // Cập nhật tên sản phẩm
            WebElement nameField = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("input[name='nameproduct']")
            ));
            nameField.clear();
            nameField.sendKeys(NEW_PRODUCT_NAME);

            // Cập nhật giá
            WebElement priceField = driver.findElement(By.cssSelector("input[name='price']"));
            priceField.clear();
            priceField.sendKeys(NEW_PRODUCT_PRICE);

            // Cập nhật mô tả
            WebElement descriptionField = driver.findElement(By.cssSelector("textarea[name='description']"));
            descriptionField.clear();
            descriptionField.sendKeys(NEW_PRODUCT_DESCRIPTION);

            // Upload ảnh mới
            WebElement fileInput = driver.findElement(By.cssSelector("input[name='photoFile']"));
            fileInput.sendKeys(NEW_IMAGE_PATH);
            System.out.println("🔹 Đã upload ảnh mới: " + NEW_IMAGE_PATH);

            

            // Submit form
            WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(@class, 'btn-primary') and contains(text(),'Lưu thay đổi')]")
            ));
            System.out.println("Đã cập nhật thông tin sản phẩm");

            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", 
                submitButton
            );
            Thread.sleep(1000);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitButton);
            System.out.println("🔹 Gửi yêu cầu cập nhật...");

            // Xác nhận cập nhật thành công
            wait.until(ExpectedConditions.urlToBe("http://localhost:8080/adminproducts"));
            
            // Verify thay đổi trong danh sách
            WebElement updatedProduct = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h5[contains(@class, 'card-title') and text()='" + NEW_PRODUCT_NAME + "']")
            ));
            
            // Verify ảnh mới (kiểm tra thuộc tính src)
            WebElement productImage = driver.findElement(
                By.xpath("//h5[text()='" + NEW_PRODUCT_NAME + "']/ancestor::div[@class='card']//img")
            );
            assertFalse("Ảnh sản phẩm chưa được cập nhật", 
                      productImage.getAttribute("src").contains("default-product.jpg"));
            
            System.out.println("✅ Cập nhật sản phẩm thành công!");
            JOptionPane.showMessageDialog(null, "✅ Cập nhật sản phẩm thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            System.err.println("❌ Lỗi trong quá trình cập nhật: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "❌ Cập nhật thất bại: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            fail(e.toString());
        } finally {
            driver.quit();
            System.out.println("🔹 Đóng trình duyệt...");
        }
    }
}