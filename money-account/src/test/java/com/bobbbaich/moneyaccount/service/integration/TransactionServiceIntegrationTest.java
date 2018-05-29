package com.bobbbaich.moneyaccount.service.integration;

import com.bobbbaich.moneyaccount.dto.TransactionDTO;
import com.bobbbaich.moneyaccount.exception.TransactionException;
import com.bobbbaich.moneyaccount.service.OrganizationService;
import com.bobbbaich.moneyaccount.service.TransactionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionServiceIntegrationTest {

    private static final long DEBIT_AMOUNT = 100L;
    private static final long CREDIT_AMOUNT = 50L;

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private OrganizationService organizationService;

    private TransactionDTO creditTransactionDTO;
    private TransactionDTO debitTransactionDTO;


    @Before
    public void setUp() {
        creditTransactionDTO = TransactionDTO.builder()
                .type("CREDIT")
                .amount(CREDIT_AMOUNT)
                .build();

        debitTransactionDTO = TransactionDTO.builder()
                .type("DEBIT")
                .amount(DEBIT_AMOUNT)
                .build();
    }

    @Test(expected = TransactionException.class)
    public void failOnAmountLessThenTotal() {
        transactionService.proceedTransaction(creditTransactionDTO);
    }

    @Test
    public void debitTransactionIncreaseTotal() {
        Long beforeTransaction = organizationService.getCurrentOrganization().get().getTotal();

        transactionService.proceedTransaction(debitTransactionDTO);
        Long afterTransaction = organizationService.getCurrentOrganization().get().getTotal();

        assertEquals(Long.valueOf(beforeTransaction + debitTransactionDTO.getAmount()), afterTransaction);
    }

    @Test
    public void debitTransactionDecreaseTotal() {
        Long beforeTransaction = organizationService.getCurrentOrganization().get().getTotal();

        transactionService.proceedTransaction(creditTransactionDTO);
        Long afterTransaction = organizationService.getCurrentOrganization().get().getTotal();

        assertEquals(Long.valueOf(beforeTransaction - creditTransactionDTO.getAmount()), afterTransaction);
    }
}
