package pages;

import base.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.util.List;

public class BookingPage extends BasePage {

    @FindBy(id = "bookingId")
    private WebElement bookingIdField;

    @FindBy(className = "room-listing")
    private List<WebElement> roomListings;

    @FindBy(className = "book-room")
    private WebElement openBookingFormButton;

    @FindBy(name = "firstname")
    private WebElement firstNameInput;

    @FindBy(name = "lastname")
    private WebElement lastNameInput;

    @FindBy(name = "roomid")
    private WebElement roomSelect;

    @FindBy(id = "depositpaid")
    private WebElement depositPaidSelect;

    @FindBy(id = "checkin")
    private WebElement checkinInput;

    @FindBy(id = "checkout")
    private WebElement checkoutInput;

    @FindBy(css = ".btn-outline-primary.book-room")
    private WebElement bookButton;

    public void createBooking(String fname, String lname, String checkin, String checkout) {
        click(openBookingFormButton);
        sendKeys(firstNameInput, fname);
        sendKeys(lastNameInput, lname);
        sendKeys(checkinInput, checkin);
        sendKeys(checkoutInput, checkout);
        click(bookButton);
    }

    public int getRoomCount() {
        return roomListings.size();
    }
}
