package com.bobbbaich.moneyaccount.service;

import com.bobbbaich.moneyaccount.dto.TransactionDTO;
import com.bobbbaich.moneyaccount.dto.TransactionHistoryDTO;

import java.util.List;

public interface TransactionService {
    List<TransactionHistoryDTO> retrieveHistory();

    void proceedTransaction(TransactionDTO transactionDto);
}