package com.bobbbaich.moneyaccount.service;

import com.bobbbaich.moneyaccount.dto.TransactionHistoryDTO;
import com.bobbbaich.moneyaccount.exception.TransactionException;
import com.bobbbaich.moneyaccount.model.Organization;
import com.bobbbaich.moneyaccount.model.Transaction;
import com.bobbbaich.moneyaccount.repository.TransactionRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class TransactionServiceTest {
    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private OrganizationService organizationService;
    @InjectMocks
    private TransactionServiceImpl transactionService;

    private Organization organization;
    private List<Transaction> transactions;
    private List<TransactionHistoryDTO> historyDTOS;

    @Before
    public void setUp() {
        organization = Organization.builder()
                .id("org_id").total(10L).transactions(new ArrayList<>())
                .build();

        TransactionHistoryDTO historyDTO = TransactionHistoryDTO.builder()
                .id("transaction_1_id")
                .build();

        historyDTOS = new ArrayList<>();
        historyDTOS.add(historyDTO);

        Transaction transaction = Transaction.builder()
                .id("transaction_1_id")
                .build();


        transactions = new ArrayList<>();
        transactions.add(transaction);
    }

    @Test
    public void retrieveHistory() {
        when(transactionRepository.getAllByOrganization(organization)).thenReturn(transactions);
        when(organizationService.getCurrentOrganization()).thenReturn(of(organization));

        List<TransactionHistoryDTO> transactionHistoryDTOS = transactionService.retrieveHistory();

        assertEquals(historyDTOS, transactionHistoryDTOS);
    }

    @Test(expected = TransactionException.class)
    public void failRetrieveHistoryForNonExistedOrganization() {
        when(organizationService.getCurrentOrganization()).thenReturn(empty());

        List<TransactionHistoryDTO> transactionHistoryDTOS = transactionService.retrieveHistory();

        assertEquals(historyDTOS, transactionHistoryDTOS);
    }
}