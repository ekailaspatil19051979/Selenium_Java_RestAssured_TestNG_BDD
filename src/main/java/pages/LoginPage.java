package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import static org.openqa.selenium.support.locators.RelativeLocator.with;

public class LoginPage extends BasePage {

    @FindBy(id = "username")
    private WebElement usernameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "doLogin")
    private WebElement loginButton;

    @FindBy(css = ".navbar-brand")
    private WebElement navBarBrand;

    public void login(String username, String password) {
        sendKeys(usernameField, username);
        sendKeys(passwordField, password);
        click(loginButton);
    }

    /**
     * Demonstrates Selenium 4 Relative Locators
     */
    public void loginWithRelativeLocators(String username, String password) {
        sendKeys(usernameField, username);
        // Find password field which is below username field
        WebElement pwd = driver.findElement(with(By.tagName("input")).below(usernameField));
        sendKeys(pwd, password);
        // Find login button which is below password field
        WebElement loginBtn = driver.findElement(with(By.tagName("button")).below(pwd));
        click(loginBtn);
    }

    public boolean isLoggedIn() {
        // Simple check, can be improved with specific element on dashboard
        return navBarBrand.isDisplayed();
    }
}
