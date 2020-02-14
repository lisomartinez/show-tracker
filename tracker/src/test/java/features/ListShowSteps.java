package features;

import io.cucumber.java8.En;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
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
