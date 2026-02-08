package pages.components;

import base.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class NavBar extends BasePage {

    @FindBy(css = ".navbar-brand")
    private WebElement homeLink;

    @FindBy(xpath = "//a[text()='Rooms']")
    private WebElement roomsLink;

    @FindBy(xpath = "//a[text()='Admin panel']")
    private WebElement adminPanelLink;

    @FindBy(id = "logout")
    private WebElement logoutLink;

    public void clickHome() {
        click(homeLink);
    }

    public void clickRooms() {
        click(roomsLink);
    }

    public void clickAdminPanel() {
        click(adminPanelLink);
    }

    public void logout() {
        click(logoutLink);
    }

    public boolean isLogoutDisplayed() {
        try {
            return logoutLink.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
