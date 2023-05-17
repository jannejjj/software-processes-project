package at.fhv.cucumber;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import io.cucumber.java.en.Then;
import org.junit.Assert;

import at.fhv.AutomaticHandling;

@CucumberContextConfiguration
public class annotation {

    private Integer equityCapital;
    private Integer creditAmount;
    private AutomaticHandling automaticHandling;
    private Boolean approved;

    @Given("^Equity capital in an application is too low$")
    public void eQTooLow() {
        creditAmount =  100000;
        equityCapital =  35000; // 35%
    }

    @Given("^Equity capital in an application is high enough$")
    public void eQHighEnough() {
        creditAmount =  100000;
        equityCapital =  45000; // 45%
    }

    @Given("^Equity capital in an application is 2 times or higher than the credit amount$")
    public void eQ2TimesOrMore() {
        creditAmount =  1000;
        equityCapital =  2000; // 200%
    }

    @When("^The application goes to automatic handling$")
    public void automaticHandling() {
        automaticHandling = new AutomaticHandling();
        approved = automaticHandling.equityRatioIsOK(creditAmount, equityCapital);
    }

    @Then("^The application will not be approved$")
    public void testNotApproved() {
        Assert.assertEquals(false, approved);
    }

    @Then("^The application will be approved$")
    public void testApproved() {
        Assert.assertEquals(true, approved);
    }

}