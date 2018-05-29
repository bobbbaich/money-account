package com.bobbbaich.moneyaccount.service;

import com.bobbbaich.moneyaccount.model.Organization;
import com.bobbbaich.moneyaccount.model.Transaction;
import com.bobbbaich.moneyaccount.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Component
@Transactional
public class DebitStrategy implements TransactionStrategy {
    private final OrganizationRepository organizationRepository;

    @Override
    public void proceed(Transaction transaction, Organization organization) {
        Long total = organization.getTotal();
        Long amount = transaction.getAmount();
        log.debug("Proceed 'debit' payment - total: [{}], amount: [{}]", total, amount);

        long transactionRes = total + amount;

        transaction.setOrganization(organization);
        transaction.setCurrentTotal(transactionRes);

        organization.setTotal(transactionRes);
        organization.getTransactions().add(transaction);

        organizationRepository.save(organization);
        log.debug("Payment proceed successfully.");
    }
}