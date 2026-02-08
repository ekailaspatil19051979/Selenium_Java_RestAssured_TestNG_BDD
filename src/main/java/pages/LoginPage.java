package pages;

import base.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

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

    public boolean isLoggedIn() {
        // Simple check, can be improved with specific element on dashboard
        return navBarBrand.isDisplayed();
    }
}
