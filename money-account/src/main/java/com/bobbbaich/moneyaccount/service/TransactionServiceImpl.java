package com.bobbbaich.moneyaccount.service;

import com.bobbbaich.moneyaccount.dto.TransactionDTO;
import com.bobbbaich.moneyaccount.dto.TransactionHistoryDTO;
import com.bobbbaich.moneyaccount.exception.TransactionException;
import com.bobbbaich.moneyaccount.model.Organization;
import com.bobbbaich.moneyaccount.model.Transaction;
import com.bobbbaich.moneyaccount.model.TransactionType;
import com.bobbbaich.moneyaccount.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Optional.of;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final OrganizationService organizationService;

    private final Map<TransactionType, TransactionStrategy> strategies;

    @Override
    @Transactional(readOnly = true)
    public List<TransactionHistoryDTO> retrieveHistory() {
        Optional<Organization> organization = organizationService.getCurrentOrganization();
        log.debug("Retrieve transaction history for organization with id [{}]", organization.map(Organization::getId).orElse(""));
        return organization
                .map(transactionRepository::getAllByOrganization)
                .map(l -> l.stream().map(t -> TransactionHistoryDTO.builder()
                        .id(t.getId())
                        .amount(t.getAmount())
                        .type(t.getType())
                        .total(t.getCurrentTotal())
                        .dateTime(t.getDateTime())
                        .build()
                ).collect(Collectors.toList()))
                .orElseThrow(TransactionException::new);
    }

    @Override
    public void proceedTransaction(TransactionDTO transactionDto) {
        Transaction transaction = initializeTransaction(transactionDto);

        log.debug("Processing transaction: [{}]", transaction.getId());
        organizationService.getCurrentOrganization()
                .map(organization -> {
                    strategies.getOrDefault(transaction.getType(), (t, o) -> {
                        log.error("There is no implementation for transaction type: [{}]", t.getType());
                        throw new TransactionException("Unsupported transaction type.");
                    })
                            .proceed(transaction, organization);
                    return organization;
                })
                .orElseThrow(() -> new TransactionException("Transaction does not belong to any organization."));
    }

    private Transaction initializeTransaction(TransactionDTO transactionDto) {
        if (ObjectUtils.isEmpty(transactionDto)) {
            log.error("Param 'transactionDto' is null");
            throw new IllegalArgumentException("Param 'transactionDto' is null");
        }


        return Transaction.builder()
                .amount(transactionDto.getAmount())
                .type(of(transactionDto.getType())
                        .map(TransactionType::valueOf)
                        .orElseThrow(TransactionException::new))
                .dateTime(LocalDateTime.now())
                .build();
    }
}