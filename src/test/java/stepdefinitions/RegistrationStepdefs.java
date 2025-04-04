package stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegistrationStepdefs {
    WebDriver driver;

    @Given("I navigate to the Basketball England registration page")
    public void iNavigateToTheBasketballEnglandRegistrationPage() {
        driver = new ChromeDriver();
        driver.get("https://membership.basketballengland.co.uk/NewSupporterAccount");
        driver.manage().window().maximize();
    }

    @When("I enter valid member details")
    public void iEnterValidMemberDetails() {
        driver.findElement(By.cssSelector("#dp")).sendKeys("14-03-1980"); // Date of birth
        driver.findElement(By.cssSelector("#member_firstname")).sendKeys("Johanna"); // First name
        driver.findElement(By.cssSelector("#member_lastname")).sendKeys("Smith"); // Last name
        driver.findElement(By.cssSelector("#member_emailaddress")).sendKeys("johanna@gmail.com"); // Email adress
        driver.findElement(By.cssSelector("#member_confirmemailaddress")).sendKeys("johanna@gmail.com"); //Confirm email adress
    }

    @And("I enter matching passwords")
    public void iEnterMatchingPasswords() {
        driver.findElement(By.cssSelector("#signupunlicenced_password")).sendKeys("abc123"); //Password
        driver.findElement(By.cssSelector("#signupunlicenced_confirmpassword")).sendKeys("abc123");  // Confirm password
    }

    @And("I accept the terms and conditions")
    public void iAcceptTheTermsAndConditions() {
        driver.findElement(By.cssSelector("#signup_form > div:nth-child(11) > div > div > div:nth-child(12) > " +
                "div > label > span.box")).click();  //Select Fan box
        driver.findElement(By.cssSelector("#signup_form > div:nth-child(12) > div > div:nth-child(2) > " +
                "div:nth-child(1) > label > span.box")).click(); // Accept terms and conditions
        driver.findElement(By.cssSelector("#signup_form > div:nth-child(12) > div > div:nth-child(2) > " +
                "div.md-checkbox.margin-top-10 > label > span.box")).click(); // Accept "I am over 18"
        driver.findElement(By.cssSelector("#signup_form > div:nth-child(12) > div > div:nth-child(4) >" +
                " label > span.box")).click();  // Receive marketing communications
        driver.findElement(By.cssSelector("#signup_form > div:nth-child(12) > div > div:nth-child(7) > " +
                "label > span.box")).click();  // Accept Code of Ethics
    }

    @And("I submit the registration form")
    public void iSubmitTheRegistrationForm() {
        driver.findElement(By.xpath("//*[@id=\"signup_form\"]/div[12]/input")).click();  // Confirm and join
    }

    @Then("I should see a confirmation that the account was created")
    public void iShouldSeeAConfirmationThatTheAccountWasCreated() {
        driver.findElement(By.cssSelector("body > div > div.page-content-wrapper > div > h2"))
                .sendKeys("THANK YOU FOR CREATING AN ACCOUNT WITH BASKETBALL ENGLAND");
    }

    @When("I enter member details without a last name")
    public void iEnterMemberDetailsWithoutALastName() {
        driver.findElement(By.cssSelector("#dp")).sendKeys("26-05-1993");
        driver.findElement(By.cssSelector("#member_firstname")).sendKeys("John");
        driver.findElement(By.cssSelector("#member_emailaddress")).sendKeys("john@gmail.com");
        driver.findElement(By.cssSelector("#member_confirmemailaddress")).sendKeys("john@gmail.com");

    }

    @Then("I should see an error message for the missing last name")
    public void iShouldSeeAnErrorMessageForTheMissingLastName() {
        WebElement error = driver.findElement(By.cssSelector("#signup_form > div:nth-child(6) > div:nth-child(2) > div > span > span"));
        assertTrue(error.isDisplayed());
        driver.quit();
    }

    @And("I enter non-matching passwords")
    public void iEnterNonMatchingPasswords() {
    }

    @Then("I should see an error message for password mismatch")
    public void iShouldSeeAnErrorMessageForPasswordMismatch() {
    }

    @And("I do not accept the terms and conditions")
    public void iDoNotAcceptTheTermsAndConditions() {
    }

    @Then("I should see an error message for unaccepted terms")
    public void iShouldSeeAnErrorMessageForUnacceptedTerms() {
    }
}
