package com.bobbbaich.moneyaccount.controller;


import com.bobbbaich.moneyaccount.dto.TransactionDTO;
import com.bobbbaich.moneyaccount.service.TransactionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class TransactionControllerTest {
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private TransactionController controller;

    @LocalServerPort
    private int port;

    private HttpHeaders headers = new HttpHeaders();

    @Test
    public void loadController() {
        assertNotNull(controller);
    }

    private static final String DEBIT = "{ \"amount\" : 100, \"type\": \"DEBIT\"}";
    private static final String CREDIT = "{ \"amount\" : 50, \"type\": \"DEBIT\"}";

    @Before
    public void setUp() {
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
    }

    @Test
    public void sendToRetrieveHistoryAndCheckStatus() {
        ResponseEntity<List> response = restTemplate.getForEntity(createURLWithPort("/transaction/history"), List.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


    @Test
    public void sendToDebitTransactionAndCheckStatus() {
        ResponseEntity response = restTemplate
                .exchange(createURLWithPort("/transaction"), HttpMethod.POST, new HttpEntity<>(DEBIT, headers), ResponseEntity.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void sendToCreditTransactionAndCheckStatus() {
        transactionService.proceedTransaction(TransactionDTO.builder().type("DEBIT").amount(100L).build());

        ResponseEntity response = restTemplate
                .exchange(createURLWithPort("/transaction"), HttpMethod.POST, new HttpEntity<>(CREDIT, headers), ResponseEntity.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
