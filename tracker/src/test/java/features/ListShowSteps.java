package features;

import io.cucumber.java8.En;

import static org.assertj.core.api.Assertions.assertThat;

public class ListShowSteps implements En {
    public ListShowSteps() {
        Given("^I am logged$", () -> {

        });
        Given("^I have a list of followed courses$", () -> {
        });
        When("^I ask for my list of shows$", () -> {
        });
        Then("^I get the list with shows\\.$", assertThat(true)::isFalse);
    }
}
