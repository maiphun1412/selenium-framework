package framework.pages;

import framework.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class CartPage extends BasePage {

    @FindBy(css = ".cart_item")
    private List<WebElement> cartItems;

    @FindBy(css = ".cart_item .inventory_item_name")
    private List<WebElement> itemNames;

    @FindBy(css = ".cart_button")
    private List<WebElement> removeButtons;

    @FindBy(id = "checkout")
    private WebElement checkoutButton;

    public CartPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public int getItemCount() {
        return safeListSize(cartItems);
    }

    public CartPage removeFirstItem() {
        if (!removeButtons.isEmpty()) {
            clickElement(removeButtons.get(0));
            sleep(300);
            PageFactory.initElements(driver, this);
        }
        return this;
    }

    public CartPage goToCheckout() {
        if (isElementDisplayed(checkoutButton)) {
            clickElement(checkoutButton);
        }
        return this;
    }

    public List<String> getItemNames() {
        List<String> names = new ArrayList<>();
        for (WebElement itemName : itemNames) {
            try {
                names.add(itemName.getText().trim());
            } catch (Exception ignored) {
            }
        }
        return names;
    }
}