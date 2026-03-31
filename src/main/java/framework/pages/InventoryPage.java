package framework.pages;

import framework.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class InventoryPage extends BasePage {

    @FindBy(css = ".inventory_list")
    private WebElement inventoryList;

    @FindBy(css = ".inventory_item")
    private List<WebElement> inventoryItems;

    @FindBy(css = ".shopping_cart_badge")
    private List<WebElement> cartBadgeList;

    @FindBy(css = ".inventory_item button")
    private List<WebElement> addToCartButtons;

    @FindBy(css = ".shopping_cart_link")
    private WebElement cartLink;

    @FindBy(xpath = "//div[@class='inventory_item_name' and text()='Sauce Labs Backpack']/ancestor::div[@class='inventory_item']//button")
    private WebElement backpackAddButton;

    public InventoryPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public boolean isLoaded() {
        return isElementDisplayed(inventoryList);
    }

    public InventoryPage addFirstItemToCart() {
        if (!addToCartButtons.isEmpty()) {
            clickElement(addToCartButtons.get(0));
        }
        return this;
    }

    public InventoryPage addItemByName(String name) {
    String xpath = "//div[contains(@class,'inventory_item_name') and contains(text(),'" + name + "')]" +
            "/ancestor::div[@class='inventory_item']//button";

    WebElement button = wait.until(
            org.openqa.selenium.support.ui.ExpectedConditions
                    .elementToBeClickable(org.openqa.selenium.By.xpath(xpath))
    );

    clickElement(button);
    return this;
}

    public int getCartItemCount() {
        try {
            if (cartBadgeList.isEmpty()) {
                return 0;
            }
            return Integer.parseInt(cartBadgeList.get(0).getText().trim());
        } catch (Exception e) {
            return 0;
        }
    }

    public CartPage goToCart() {
        clickElement(cartLink);
        return new CartPage(driver);
    }
}