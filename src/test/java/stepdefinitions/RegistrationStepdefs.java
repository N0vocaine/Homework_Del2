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
    //String uniqecode = String.valueOf(UUID.randomUUID());
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
        //driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://membership.basketballengland.co.uk/NewSupporterAccount");

    }

    @When("I enter valid member details")
    public void iEnterValidMemberDetails() {
        driver.findElement(By.cssSelector("#dp")).sendKeys("14-03-1981"); // Date of birth
        driver.findElement(By.cssSelector("#member_firstname")).sendKeys("Lara"); // First name
        driver.findElement(By.cssSelector("#member_lastname")).sendKeys("Thomson"); // Last name
        driver.findElement(By.cssSelector("#member_emailaddress")).sendKeys(email); // Email adress
        driver.findElement(By.cssSelector("#member_confirmemailaddress")).sendKeys(email); //Confirm email adress
    }

    @And("I enter matching passwords")
    public void iEnterMatchingPasswords() {
        driver.findElement(By.cssSelector("#signupunlicenced_password")).sendKeys("abc123"); //Password
        driver.findElement(By.cssSelector("#signupunlicenced_confirmpassword")).sendKeys("abc123");  // Confirm password
    }

    @And("I accept the terms and conditions")
    public void iAcceptTheTermsAndConditions() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        waitAndClick("#signup_form > div:nth-child(11) > " +
                "div > div > div:nth-child(12) > div > label > span.box");  // Wait for and click the first checkbox

        waitAndClick("#signup_form > " +
                "div:nth-child(12) > div > div:nth-child(2) > div:nth-child(1) > label > span.box");  // Wait for and click the second checkbox

        waitAndClick("#signup_form > div:nth-child(12) > div > " +
                "div:nth-child(2) > div.md-checkbox.margin-top-10 > label > span.box");// Wait for and click the third checkbox

        waitAndClick("#signup_form > div:nth-child(12) > div > " +
                "div:nth-child(4) > label > span.box"); // Wait for and click the fourth checkbox

        waitAndClick("#signup_form > div:nth-child(12) > div > " +
                "div:nth-child(7) > label > span.box");  // Wait for and click the fifth checkbox
    }

    @Then("I submit the registration form")
    public void iSubmitTheRegistrationForm() {
        waitAndClick("#signup_form > div.form-actions.noborder > input");   // Confirm and join


    }

    @And("I should see a success message")
    public void iShouldSeeASuccessMessage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement successMessage = wait.until(ExpectedConditions.elementToBeClickable(By
                .cssSelector("body > div > div.page-content-wrapper > div > h2")));
        assertTrue(successMessage.isDisplayed());
    }


    @When("I enter member details without a last name")
    public void iEnterMemberDetailsWithoutALastName() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.findElement(By.cssSelector("#dp")).sendKeys("14-03-1980");// Date of birth
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#dp")));

        driver.findElement(By.cssSelector("#member_firstname")).sendKeys("Johanna"); // First name
        // driver.findElement(By.cssSelector("#member_lastname")).sendKeys(" "); // Last name
        driver.findElement(By.cssSelector("#member_emailaddress")).sendKeys("johanna@gmail.com"); // Email adress
        driver.findElement(By.cssSelector("#member_confirmemailaddress")).sendKeys("johanna@gmail.com"); //Confirm email adress
        driver.findElement(By.cssSelector("#signupunlicenced_password")).sendKeys("def456"); // Password
        driver.findElement(By.cssSelector("#signupunlicenced_confirmpassword")).sendKeys("def456");  //Confirm password
        iAcceptTheTermsAndConditions();


        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#signup_form > div.form-actions.noborder > input")));
        driver.findElement(By.cssSelector("#signup_form > div.form-actions.noborder > input")).click();


    }

    @Then("I should see an error message for the missing last name")
    public void iShouldSeeAnErrorMessageForTheMissingLastName() {
        //WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement error = driver.findElement(By.cssSelector("#signup_form > div:nth-child(6) > div:nth-child(2) > div > span > span"));
        assertTrue(error.isDisplayed(), "Last name is required");
        //driver.quit();
    }

    @And("I enter non-matching passwords")
    public void iEnterNonMatchingPasswords() {
        driver.findElement(By.cssSelector("#signupunlicenced_password")).sendKeys("abc123");
        driver.findElement(By.cssSelector("#signupunlicenced_confirmpassword")).sendKeys("def456"); // Wrong password
        iAcceptTheTermsAndConditions();
    }

    @Then("I should see an error message for password mismatch")
    public void iShouldSeeAnErrorMessageForPasswordMismatch() {
        //WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        WebElement error = driver.findElement((By.cssSelector("#signup_form > div:nth-child(9) > div > div.row > div:nth-child(2) > div > span > span")));
        assertTrue(error.isDisplayed(), "Password did not match");
        //driver.quit();
    }

    @And("I do not accept the terms and conditions")
    public void iDoNotAcceptTheTermsAndConditions() {
    }

    @Then("I should see an error message for unaccepted terms")
    public void iShouldSeeAnErrorMessageForUnacceptedTerms() {
        //WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement error = driver.findElement(By.cssSelector("#signup_form > div:nth-child(12) > " +
                "div > div:nth-child(2) > div:nth-child(1) > span > span"));
        assertTrue(error.isDisplayed());
        //driver.quit();
    }


}





