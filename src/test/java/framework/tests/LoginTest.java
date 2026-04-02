package framework.tests;

import framework.pages.InventoryPage;
import framework.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.qameta.allure.*;

public class LoginTest extends BaseTest {
    
    private String getEnvOrDefault(String key, String defaultValue) {
    String value = System.getenv(key);
    return (value == null || value.isBlank()) ? defaultValue : value;
}

    @Feature("Login")
@Story("Login thành công")
@Severity(SeverityLevel.CRITICAL)
@Description("Đăng nhập với tài khoản hợp lệ")
@Test
    public void testLoginWithValidAccount() {
        Allure.step("Mở trang login");
LoginPage loginPage = new LoginPage(driver);

String username = getEnvOrDefault("APP_USERNAME", "standard_user");
String password = getEnvOrDefault("APP_PASSWORD", "secret_sauce");

Allure.step("Nhập username và password");
InventoryPage inventoryPage = loginPage.login(username, password);

Allure.step("Xác nhận vào InventoryPage");
Assert.assertTrue(inventoryPage.isLoaded(), "Đăng nhập hợp lệ nhưng không vào được InventoryPage");
    }
   @Feature("Login")
@Story("Sai password")
@Severity(SeverityLevel.NORMAL)
@Description("Đăng nhập với mật khẩu không đúng")
@Test
    public void testLoginWithInvalidPassword() {
        LoginPage loginPage = new LoginPage(driver);
String username = getEnvOrDefault("APP_USERNAME", "standard_user");

Allure.step("Nhập username hợp lệ và password sai");
loginPage.loginExpectingFailure(username, "wrong_password");

Allure.step("Xác nhận hiển thị thông báo lỗi");
Assert.assertTrue(loginPage.isErrorDisplayed(), "Không hiển thị thông báo lỗi");
Assert.assertTrue(loginPage.getErrorMessage().toLowerCase().contains("username and password do not match"),
        "Thông báo lỗi sai password không đúng");
    }

    @Feature("Login")
@Story("User bị khóa")
@Severity(SeverityLevel.CRITICAL)
@Description("User locked không được login")
@Test
    public void testLoginWithLockedUser() {
        LoginPage loginPage = new LoginPage(driver);
String password = getEnvOrDefault("APP_PASSWORD", "secret_sauce");

Allure.step("Nhập locked user với password hợp lệ");
loginPage.loginExpectingFailure("locked_out_user", password);

Allure.step("Xác nhận hiển thị thông báo locked out");
Assert.assertTrue(loginPage.isErrorDisplayed(), "Không hiển thị thông báo lỗi cho locked user");
Assert.assertTrue(loginPage.getErrorMessage().toLowerCase().contains("locked out"),
        "Thông báo locked user không đúng");
    }
}