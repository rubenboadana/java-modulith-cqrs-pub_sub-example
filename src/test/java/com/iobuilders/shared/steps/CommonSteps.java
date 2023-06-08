package com.iobuilders.shared.steps;

import com.iobuilders.shared.utils.ScenarioContext;
import io.cucumber.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class CommonSteps {

    @Autowired
    private ScenarioContext context;

    @Then("^response code is \"([^\"]*)\"$")
    public void checkResponseCode(int expectedCode) {
        ResponseEntity<String> response = context.getResponse();
        assertThat(response.getStatusCode().value()).isEqualTo(expectedCode);
    }

    @Then("^response body is:$")
    public void checkResponseBody(String expectedBody) {
        ResponseEntity<String> response = context.getResponse();
        assertThat(response.getBody()).isEqualTo(expectedBody);
    }

    @Then("^response body is like:$")
    public void checkSimilarResponseBody(String expectedBody) {
        ResponseEntity<String> response = context.getResponse();
        assertThat(response.getBody()).startsWith(expectedBody);
    }

}