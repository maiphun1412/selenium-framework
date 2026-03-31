package framework.pages;

import framework.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BasePage {

    @FindBy(id = "user-name")
    private WebElement usernameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    @FindBy(css = "h3[data-test='error']")
    private WebElement errorMessage;

    public LoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public boolean isLoaded() {
        return isElementDisplayed(usernameField)
                && isElementDisplayed(passwordField)
                && isElementDisplayed(loginButton);
    }

    public InventoryPage login(String user, String pass) {
        typeText(usernameField, user);
        typeText(passwordField, pass);
        clickElement(loginButton);
        return new InventoryPage(driver);
    }

    public LoginPage loginExpectingFailure(String user, String pass) {
        typeText(usernameField, user);
        typeText(passwordField, pass);
        clickElement(loginButton);
        return this;
    }

    public String getErrorMessage() {
        try {
            return errorMessage.getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    public boolean isErrorDisplayed() {
        return isElementDisplayed(errorMessage);
    }
}