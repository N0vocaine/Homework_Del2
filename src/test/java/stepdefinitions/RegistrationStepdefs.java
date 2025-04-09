package stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegistrationStepdefs {
    WebDriver driver;
    String email = "lara+" + System.currentTimeMillis() + "@gmail.com";

    private void waitAndClick(String css) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(css)));
        element.click();
    }

    @Given("I navigate to the Basketball England registration page using {string}")
    public void iNavigateToTheBasketballEnglandRegistrationPage(String browser) {
        if (browser.equals("chrome")) driver = new ChromeDriver();
        if (browser.equals("edge")) driver = new EdgeDriver();

        driver.manage().window().maximize();
        driver.get("https://membership.basketballengland.co.uk/NewSupporterAccount");

    }

    @When("I enter valid member details")
    public void iEnterValidMemberDetails() {
        driver.findElement(By.cssSelector(".custom-date")).sendKeys("14-03-1981"); // Date of birth
        driver.findElement(By.cssSelector("#member_firstname")).sendKeys("Lara"); // First name
        driver.findElement(By.cssSelector("#member_lastname")).sendKeys("Thomson"); // Last name
        driver.findElement(By.cssSelector("#member_emailaddress")).sendKeys(email); // Email address
        driver.findElement(By.cssSelector("#member_confirmemailaddress")).sendKeys(email); //Confirm email address
    }

    @And("I enter matching passwords")
    public void iEnterMatchingPasswords() {
        driver.findElement(By.cssSelector("#signupunlicenced_password"))
                .sendKeys("abc123"); //Password
        driver.findElement(By.cssSelector("#signupunlicenced_confirmpassword"))
                .sendKeys("abc123");  // Confirm password
    }

    @And("I accept the terms and conditions")
    public void iAcceptTheTermsAndConditions() {

        waitAndClick("label[for='signup_basketballrole_19']");// Wait for and click the first checkbox
        waitAndClick("label[for='sign_up_25']");// Wait for and click the second checkbox
        waitAndClick("label[for='sign_up_26']");   // Wait for and click the third checkbox
        waitAndClick("label[for='sign_up_27']");   // Wait for and click the fourth checkbox
        waitAndClick("label[for='fanmembersignup_agreetocodeofethicsandconduct']"); // Wait for and click the fifth checkbox
    }

    @Then("I submit the registration form")
    public void iSubmitTheRegistrationForm() {
        waitAndClick("input[value='CONFIRM AND JOIN']");   // Confirm and join

    }

    @And("I should see a success message")
    public void iShouldSeeASuccessMessage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement successMessage = wait.until(ExpectedConditions.elementToBeClickable(By
                .cssSelector("body > div > div.page-content-wrapper > div > div > h2")));
        assertTrue(successMessage.isDisplayed());
        System.out.println("Your Membership Number is: " + successMessage.getText());
    }


    @When("I enter member details without a last name")
    public void iEnterMemberDetailsWithoutALastName() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.findElement(By.cssSelector(".custom-date")).sendKeys("14-03-1981");// Date of birth
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".custom-date")));
        driver.findElement(By.cssSelector("#member_firstname"))
                .sendKeys("Lara"); // First name
        driver.findElement(By.cssSelector("#member_emailaddress")).sendKeys(email); // Email address
        driver.findElement(By.cssSelector("#member_confirmemailaddress")).sendKeys(email); //Confirm email address
        iAcceptTheTermsAndConditions();

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#signup_form > div.form-actions.noborder > input")));
        driver.findElement(By.cssSelector("#signup_form > div.form-actions.noborder > input")).click();
    }

    @Then("I should see an error message for the missing last name")
    public void iShouldSeeAnErrorMessageForTheMissingLastName() {
        WebElement error = driver.findElement(By.cssSelector("span.warning.field-validation-error"));
        assertTrue(error.isDisplayed(), "Last name is required");
        System.out.println("Last name is required");

    }

    @And("I enter non-matching passwords")
    public void iEnterNonMatchingPasswords() {
        driver.findElement(By.cssSelector("#signupunlicenced_password"))
                .sendKeys("abc123");
        driver.findElement(By.cssSelector("#signupunlicenced_confirmpassword"))
                .sendKeys("def456"); // Wrong password
        iAcceptTheTermsAndConditions();
    }

    @Then("I should see an error message for password mismatch")
    public void iShouldSeeAnErrorMessageForPasswordMismatch() {

        WebElement error = driver.findElement((By
                .cssSelector("span[for='signupunlicenced_confirmpassword']")));
        assertTrue(error.isDisplayed(), "Password did not match");
        System.out.println("Password did not match");
    }

    @And("I do not accept the terms and conditions")
    public void iDoNotAcceptTheTermsAndConditions() {    // Intentionally left empty
    }

    @Then("I should see an error message for unaccepted terms")
    public void iShouldSeeAnErrorMessageForUnacceptedTerms() {

        WebElement error = driver.findElement(By.cssSelector("a[href='/Documents/Terms and Conditions.pdf']"));
        assertTrue(error.isDisplayed());
        System.out.println("You must accept the terms and conditions!");

    }
}





