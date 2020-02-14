package features.find;

import com.github.lmartinez84.showtracker.tracker.show.application.show.ShowResponse;
import com.github.lmartinez84.showtracker.tracker.show.application.show.find.ShowFinder;
import com.github.lmartinez84.showtracker.tracker.show.domain.show.ShowProjectionRepository;
import io.cucumber.java8.En;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {ShowFindController.class, ShowFinder.class, ShowProjectionRepository.class})
public final class FindShowSteps implements En {


    private Flux<ShowResponse> response;


    public FindShowSteps() {
        Given("^There is a list of existing shows$", () -> {

        });
        When("^I ask for a show by title$", () -> {
            response = WebClient.create().get().uri("http://localhost:8080/shows/search?name=\"Lost\"")
                                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                                .retrieve()
                                .bodyToFlux(ShowResponse.class);


        });
        Then("^I get the show's information$", () -> {
            StepVerifier.create(response)
                        .assertNext(res -> assertThat(res.title()).isEqualTo("Lost"))
                        .verifyComplete();
        });
    }
}
