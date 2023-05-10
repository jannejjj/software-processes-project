package at.fhv.cucumber;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import io.cucumber.java.en.Then;
import org.junit.Assert;

import at.fhv.AutomaticHandling;
import camundajar.impl.scala.Console;

@CucumberContextConfiguration
public class Annotation {

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

    @When("^The application goes to automatic handling$")
    public void automaticHandling() {
        automaticHandling = new AutomaticHandling();
        approved = automaticHandling.equityRatioIsOK(creditAmount, equityCapital);

        System.err.println(approved);
    }

    @Then("^The application will not be approved$")
    public void testNotApproved() {
        Assert.assertEquals(false, approved);
    }

    @Then("^The application will be approved$")
    public void testApproved() {
        System.err.println(approved);
        Assert.assertEquals(true, approved);
    }

}