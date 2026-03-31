package framework.base;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    protected void clickElement(WebElement element) {
        int attempts = 0;

        while (attempts < 3) {
            try {
                element.click();
                return;
            } catch (StaleElementReferenceException e) {
                attempts++;
                sleep(300);
            } catch (Exception e) {
                try {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
                    return;
                } catch (Exception ignored) {
                    attempts++;
                    sleep(300);
                }
            }
        }

        throw new RuntimeException("Không thể click element sau nhiều lần thử");
    }

    protected void typeText(WebElement element, String text) {
        element.clear();
        element.sendKeys(text);
    }

    protected boolean isElementDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    protected int safeListSize(List<WebElement> elements) {
        return elements == null ? 0 : elements.size();
    }

    protected void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}