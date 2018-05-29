package com.bobbbaich.moneyaccount.controller;

import com.bobbbaich.moneyaccount.dto.TransactionDTO;
import com.bobbbaich.moneyaccount.dto.TransactionHistoryDTO;
import com.bobbbaich.moneyaccount.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/transaction")
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<?> transactions(@RequestBody TransactionDTO transactionDto) {
        transactionService.proceedTransaction(transactionDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/history")
    public ResponseEntity<List<TransactionHistoryDTO>> transactions() {
        return ResponseEntity.ok().body(transactionService.retrieveHistory());
    }
}