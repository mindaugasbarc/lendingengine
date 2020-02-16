package com.peerlender.lendingengine;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.peerlender.lendingengine.application.model.LoanApplicationDTO;
import com.peerlender.lendingengine.application.model.LoanRequest;
import com.peerlender.lendingengine.domain.model.LoanApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class LoanIntegrationTest {

    private static final String JOHN = "John";
    private static final String PETER = "Peter";
    private static final Gson GSON = new Gson();

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int serverPort;

    @Test
    public void givenLoanRequestIsMadeThenLoanApplicationGetsCreated() throws Exception {
        final String baseUrl = "http://localhost:" + serverPort + "/loan/";
        HttpEntity<LoanRequest> request = new HttpEntity<>(new LoanRequest(50, 10, 10),
                headers(JOHN));

        restTemplate.postForEntity(baseUrl + "/request", request, String.class);
        ResponseEntity<String> response = restTemplate
                .exchange(baseUrl + "/requests", HttpMethod.GET,
                new HttpEntity<>(null, headers(JOHN)), String.class);

        List<LoanApplicationDTO> loanApplications  = GSON.fromJson(response.getBody(),
                new TypeToken<List<LoanApplicationDTO>>(){}.getType());

        assertEquals(1, loanApplications.size());
        assertEquals(loanApplications.get(0).getBorrower().getUsername(), JOHN);
    }

    private HttpHeaders headers(final String username) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.AUTHORIZATION, username);
        return httpHeaders;
    }
}
