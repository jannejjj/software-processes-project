package at.fhv.cucumber;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.junit.Assert;

import at.fhv.AutomaticHandling;

public class annotation {

    private int equityCapital;
    private int creditAmount;
    private AutomaticHandling automaticHandling;
    private Boolean approved;

    @Given("^Equity capital in an application is too low$")
    public void EQTooLow() {
        creditAmount = 100000;
        equityCapital = 35000; // 35%
    }

    @Given("^Equity capital in an application is high enough$")
    public void EQHighEnough() {
        creditAmount = 100000;
        equityCapital = 45000; // 45%
    }

    @When("^The application goes to automatic handling$")
    public void automaticHandling() {
        automaticHandling = new AutomaticHandling();
        approved = automaticHandling.equityRatioIsOK(creditAmount, equityCapital);
    }

    @Then("^The application will not be approved$")
    public void testApproved() {
        Assert.assertEquals(approved, false);
    }

    @Then("^The application will be approved$")
    public void testNotApproved() {
        Assert.assertEquals(approved, true);
    }

}