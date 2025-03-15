package CRUD;

import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import static org.junit.Assert.*;

public class testremoveProduct {

    final String PRODUCT_NAME = "iPhone 15 Pro Max Updated";

    @Test
    public void testRemoveProduct() {
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver-win64\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        try {
            System.out.println("🔄 Bắt đầu kiểm thử xóa sản phẩm...");

            // Bước 2: Đi đến trang quản lý sản phẩm
            System.out.println("➡️ Điều hướng đến trang quản lý sản phẩm...");
            driver.get("http://localhost:8080/adminproducts");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".admin-container")));

            // Bước 3: Tìm và xác nhận xóa
            WebElement deleteButton = null;
            try {
                System.out.println("🔍 Tìm sản phẩm cần xóa: " + PRODUCT_NAME);
                deleteButton = findDeleteButton(driver, wait);
            } catch (NoSuchElementException e) {
                System.out.println("❌ Không tìm thấy sản phẩm: " + PRODUCT_NAME);
            }

            if (deleteButton != null) {
                System.out.println("🖱️ Nhấn vào nút xóa...");
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", deleteButton);

                // Xử lý confirm dialog
                System.out.println("⚠️ Hiển thị hộp thoại xác nhận xóa...");
                WebDriverWait alertWait = new WebDriverWait(driver, Duration.ofSeconds(5));
                Alert alert = alertWait.until(ExpectedConditions.alertIsPresent());
                alert.accept();
                System.out.println("✅ Đã xác nhận xóa!");

                // Bước 4: Verify xóa thành công
                verifyDeletion(driver, wait);
            } else {
                System.out.println("⏭️ Bỏ qua kiểm thử vì không có sản phẩm để xóa.");
            }

        } catch (Exception e) {
            handleError(e);
        } finally {
            driver.quit();
            System.out.println("🔚 Kiểm thử kết thúc.");
        }
    }

    private WebElement findDeleteButton(WebDriver driver, WebDriverWait wait) {
        System.out.println("⏳ Chờ danh sách sản phẩm hiển thị...");
        wait.until(d -> !driver.findElements(By.cssSelector(".admin-card")).isEmpty());

        return driver.findElements(By.cssSelector(".admin-card")).stream()
            .filter(card -> {
                try {
                    String title = card.findElement(By.cssSelector(".card-title")).getText();
                    System.out.println("📌 Tìm thấy sản phẩm: " + title);
                    return title.equals(PRODUCT_NAME);
                } catch (StaleElementReferenceException e) {
                    return false;
                }
            })
            .findFirst()
            .map(card -> {
                scrollToElement(driver, card);
                System.out.println("🗑️ Xác định nút xóa cho sản phẩm...");
                return card.findElement(By.xpath(".//button[contains(.,'Xóa')]"));
            })
            .orElseThrow(() -> new NoSuchElementException("❌ Không tìm thấy sản phẩm cần xóa"));
    }

    private void verifyDeletion(WebDriver driver, WebDriverWait wait) {
        System.out.println("⏳ Chờ sản phẩm biến mất khỏi danh sách...");
        wait.until(ExpectedConditions.invisibilityOfElementWithText(
            By.cssSelector(".card-title"), PRODUCT_NAME
        ));

        System.out.println("🔄 Làm mới trang để kiểm tra lại...");
        driver.navigate().refresh();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".admin-container")));

        boolean productExists = driver.findElements(By.xpath("//h5[contains(.,'" + PRODUCT_NAME + "')]")).isEmpty();
        if (productExists) {
            System.out.println("✅ Sản phẩm đã bị xóa thành công!");
        } else {
            System.out.println("❌ Sản phẩm vẫn tồn tại sau khi xóa!");
        }
        assertTrue("Sản phẩm vẫn tồn tại", productExists);
    }

    private void scrollToElement(WebDriver driver, WebElement element) {
        System.out.println("🔽 Cuộn đến sản phẩm trong danh sách...");
        ((JavascriptExecutor) driver).executeScript(
            "arguments[0].scrollIntoView({behavior: 'instant', block: 'center', inline: 'center'});", 
            element
        );
    }

    private void handleError(Exception e) {
        System.err.println("❌ Lỗi: " + e.getMessage());
        e.printStackTrace();
        fail("Test thất bại: " + e.getMessage());
    }
}
