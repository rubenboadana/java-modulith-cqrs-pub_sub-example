package com.iobuilders.infrastructure.steps;

import com.iobuilders.ScenarioContext;
import com.iobuilders.domain.dto.Wallet;
import com.iobuilders.http.HttpClient;
import com.iobuilders.http.HttpMethod;
import com.iobuilders.http.HttpRequest;
import com.iobuilders.wallet.domain.WalletObjectMother;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class WalletSteps {
    private static final String VALID_WALLET = "valid";
    private static final int DEFAULT_WALLET_ID = 1;
    private static final int NOT_EXISTING_WALLET_ID = 40;
    @Autowired
    private HttpClient httpClient;

    @Autowired
    private ScenarioContext context;

    @Given("a valid wallet is available")
    public void walletIsAlreadyCreated() {
        ResponseEntity<String> response = doCreateRequest();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @When("^valid wallet creation request is sent$")
    public void walletCreationIsRequested() {
        ResponseEntity<String> response = doCreateRequest();
        context.setResponse(response);
    }

    @When("^(valid|invalid) wallet delete request is sent$")
    public void walletDeleteIsRequested(String requestType) {
        int walletID = VALID_WALLET.equals(requestType) ? DEFAULT_WALLET_ID : NOT_EXISTING_WALLET_ID;
        ResponseEntity<String> response = doDeleteRequest(walletID);
        context.setResponse(response);
    }

    private ResponseEntity<String> doCreateRequest() {
        Wallet wallet = WalletObjectMother.basic();

        final HttpRequest<Wallet> httpRequest = HttpClient.createHttpRequest(HttpMethod.POST, "/wallets", wallet);
        return httpClient.doRequest(httpRequest);

    }

    private ResponseEntity<String> doDeleteRequest(int walletId) {
        Wallet wallet = WalletObjectMother.basic();

        final HttpRequest<Wallet> httpRequest = HttpClient.createHttpRequest(HttpMethod.DELETE, "/wallets/" + walletId, wallet);
        return httpClient.doRequest(httpRequest);
    }

}