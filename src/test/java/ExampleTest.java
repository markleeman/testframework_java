import customobjects.User;
import framework.DriverWrapper;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import pageobjects.EmailSent;
import pageobjects.ForgotPassword;
import pageobjects.LoginPage;
import pageobjects.SecureArea;

import static org.testng.Assert.assertTrue;

public class ExampleTest {

    private DriverWrapper driver;

    // Example test using an explicitly defined browser and Selenium Grid option
    @Test
    public void passwordReset(){

        driver = new DriverWrapper(DriverWrapper.browsers.CHROME_HEADLESS, false);

        String confirmMessage = "Your e-mail's been sent!";

        User testUser = User.createNewRandomUser();

        EmailSent reset = new ForgotPassword(driver)
                .enterEmail(testUser.getEmailAddress())
                .submitFormWithButton();

        assertTrue(reset.getMessageText().contains(confirmMessage));
    }

    // Example test using an explicitly defined browser and default Selenium Grid option
    @Test
    public void invalidUsername(){

        driver = new DriverWrapper(DriverWrapper.browsers.SAFARI);

        String errorMessage = "Your username is invalid!";

        User testUser = User.createNewRandomUser();

        LoginPage login = new LoginPage(driver)
                .enterUserDetails(testUser)
                .loginExpectingError();

        assertTrue(login.getMessageText().contains(errorMessage));
    }

    // Example test using system properties to specify the browser and os
    @Test
    public void validLogin(){

        driver = DriverWrapper.createFromSystemProperties();

        String loggedInMessage = "You logged into a secure area!";

        User testUser = User.createNewRandomUser();
        testUser.setUsername("tomsmith");
        testUser.setPassword("SuperSecretPassword!");

        SecureArea secure = new LoginPage(driver)
                .enterUserDetails(testUser)
                .loginExpectingSuccess();

        assertTrue(secure.getMessageText().contains(loggedInMessage));
    }

    @AfterClass
    public void teardown() {
        driver.shutDown();
    }
}