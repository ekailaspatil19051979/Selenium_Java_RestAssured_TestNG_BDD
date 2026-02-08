package pages;

import base.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.components.NavBar;

public class AdminPage extends BasePage {

    // Composition: AdminPage has a NavBar
    public NavBar navBar;

    @FindBy(id = "roomNumber")
    private WebElement roomNumberInput;

    @FindBy(id = "roomPrice")
    private WebElement roomPriceInput;

    @FindBy(id = "createRoom")
    private WebElement createRoomButton;

    @FindBy(xpath = "//div[@class='room-listing']")
    private WebElement lastRoomListing;

    public AdminPage() {
        super();
        this.navBar = new NavBar();
    }

    public void createRoom(String number, String price) {
        sendKeys(roomNumberInput, number);
        sendKeys(roomPriceInput, price);
        click(createRoomButton);
    }

    public boolean isRoomDisplayed(String roomNumber) {
        // Implementation would check the list of rooms for the specific number
        // This is a placeholder for the logic
        return true;
    }

    public void logout() {
        navBar.logout();
    }
}
