package framework.tests;

import framework.pages.InventoryPage;
import framework.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {
    private String getEnvOrDefault(String key, String defaultValue) {
    String value = System.getenv(key);
    return (value == null || value.isBlank()) ? defaultValue : value;
}

    @Test
    public void testLoginWithValidAccount() {
        LoginPage loginPage = new LoginPage(driver);
        String username = getEnvOrDefault("APP_USERNAME", "standard_user");
String password = getEnvOrDefault("APP_PASSWORD", "secret_sauce");
InventoryPage inventoryPage = loginPage.login(username, password);
        Assert.assertTrue(inventoryPage.isLoaded(), "Đăng nhập hợp lệ nhưng không vào được InventoryPage");
    }

    @Test
    public void testLoginWithInvalidPassword() {
        LoginPage loginPage = new LoginPage(driver);
        String username = getEnvOrDefault("APP_USERNAME", "standard_user");
loginPage.loginExpectingFailure(username, "wrong_password");

        Assert.assertTrue(loginPage.isErrorDisplayed(), "Không hiển thị thông báo lỗi");
        Assert.assertTrue(loginPage.getErrorMessage().toLowerCase().contains("username and password do not match"),
                "Thông báo lỗi sai password không đúng");
    }

    @Test
    public void testLoginWithLockedUser() {
        LoginPage loginPage = new LoginPage(driver);
        String password = getEnvOrDefault("APP_PASSWORD", "secret_sauce");
loginPage.loginExpectingFailure("locked_out_user", password);

        Assert.assertTrue(loginPage.isErrorDisplayed(), "Không hiển thị thông báo lỗi cho locked user");
        Assert.assertTrue(loginPage.getErrorMessage().toLowerCase().contains("locked out"),
                "Thông báo locked user không đúng");
    }
}