package at.fhv.cucumber;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/java/at/fhv/cucumber/AutomaticHandling.feature", glue = "at.fhv.cucumber")
public class CucumberTest {
}