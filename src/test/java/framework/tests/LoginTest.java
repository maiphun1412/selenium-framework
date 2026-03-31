package framework.tests;

import framework.pages.InventoryPage;
import framework.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test
    public void testLoginWithValidAccount() {
        LoginPage loginPage = new LoginPage(driver);
        InventoryPage inventoryPage = loginPage.login("standard_user", "secret_sauce");

        Assert.assertTrue(inventoryPage.isLoaded(), "Đăng nhập hợp lệ nhưng không vào được InventoryPage");
    }

    @Test
    public void testLoginWithInvalidPassword() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginExpectingFailure("standard_user", "wrong_password");

        Assert.assertTrue(loginPage.isErrorDisplayed(), "Không hiển thị thông báo lỗi");
        Assert.assertTrue(loginPage.getErrorMessage().toLowerCase().contains("username and password do not match"),
                "Thông báo lỗi sai password không đúng");
    }

    @Test
    public void testLoginWithLockedUser() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginExpectingFailure("locked_out_user", "secret_sauce");

        Assert.assertTrue(loginPage.isErrorDisplayed(), "Không hiển thị thông báo lỗi cho locked user");
        Assert.assertTrue(loginPage.getErrorMessage().toLowerCase().contains("locked out"),
                "Thông báo locked user không đúng");
    }
}