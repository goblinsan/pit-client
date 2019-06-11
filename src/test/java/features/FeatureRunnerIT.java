package features;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/features"},
        glue = {"features"},
        plugin = {"pretty", "json:target/cucumber-html-reports/json/simple-trader.json"}
)
public class FeatureRunnerIT {
}
