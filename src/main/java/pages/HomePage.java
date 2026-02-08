package pages;

import base.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

    @FindBy(css = "img.hotel-logoUrl")
    private WebElement logo;

    @FindBy(xpath = "//button[text()='Book this room']")
    private WebElement bookRoomButton;

    @FindBy(name = "name")
    private WebElement contactName;

    @FindBy(name = "email")
    private WebElement contactEmail;

    @FindBy(name = "phone")
    private WebElement contactPhone;

    @FindBy(name = "subject")
    private WebElement contactSubject;

    @FindBy(name = "description")
    private WebElement contactDescription;

    @FindBy(id = "submitContact")
    private WebElement submitContactButton;

    public boolean isLogoDisplayed() {
        return logo.isDisplayed();
    }

    public void submitContactForm(String name, String email, String phone, String subject, String message) {
        sendKeys(contactName, name);
        sendKeys(contactEmail, email);
        sendKeys(contactPhone, phone);
        sendKeys(contactSubject, subject);
        sendKeys(contactDescription, message);
        click(submitContactButton);
    }
}
