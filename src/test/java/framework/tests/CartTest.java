package framework.tests;

import framework.pages.CartPage;
import framework.pages.InventoryPage;
import framework.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class CartTest extends BaseTest {

    @Test
    public void testAddFirstItemToCart() {
        LoginPage loginPage = new LoginPage(driver);
        InventoryPage inventoryPage = loginPage.login("standard_user", "secret_sauce")
                                               .addFirstItemToCart();

        Assert.assertEquals(inventoryPage.getCartItemCount(), 1, "Số lượng item trong cart badge phải là 1");
    }

    @Test
    public void testAddSpecificItemToCart() {
        LoginPage loginPage = new LoginPage(driver);
        InventoryPage inventoryPage = loginPage.login("standard_user", "secret_sauce")
                                               .addItemByName("Sauce Labs Backpack");

        Assert.assertEquals(inventoryPage.getCartItemCount(), 1, "Thêm item theo tên nhưng cart badge không tăng đúng");
    }

    @Test
    public void testFluentInterfaceGoToCart() {
        LoginPage loginPage = new LoginPage(driver);
        CartPage cartPage = loginPage.login("standard_user", "secret_sauce")
                                     .addFirstItemToCart()
                                     .goToCart();

        Assert.assertEquals(cartPage.getItemCount(), 1, "Fluent interface hoạt động sai, số item trong cart không đúng");
    }

    @Test
    public void testRemoveFirstItemFromCart() {
        LoginPage loginPage = new LoginPage(driver);
        CartPage cartPage = loginPage.login("standard_user", "secret_sauce")
                                     .addFirstItemToCart()
                                     .goToCart()
                                     .removeFirstItem();

        Assert.assertEquals(cartPage.getItemCount(), 0, "Xóa item đầu tiên nhưng cart vẫn còn item");
    }

    @Test
    public void testGetItemNamesInCart() {
        LoginPage loginPage = new LoginPage(driver);
        CartPage cartPage = loginPage.login("standard_user", "secret_sauce")
                                     .addItemByName("Sauce Labs Backpack")
                                     .goToCart();

        List<String> itemNames = cartPage.getItemNames();

        Assert.assertTrue(itemNames.contains("Sauce Labs Backpack"), "Cart không chứa đúng tên sản phẩm đã thêm");
    }

    @Test
    public void testCartWithoutItemReturnsZero() {
        LoginPage loginPage = new LoginPage(driver);
        CartPage cartPage = loginPage.login("standard_user", "secret_sauce")
                                     .goToCart();

        Assert.assertEquals(cartPage.getItemCount(), 0, "Giỏ hàng rỗng phải trả về 0");
    }
}