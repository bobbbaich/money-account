package com.bobbbaich.moneyaccount.service;

import com.bobbbaich.moneyaccount.exception.TransactionException;
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
public class CreditStrategy implements TransactionStrategy {
    private final OrganizationRepository organizationRepository;

    @Override
    public void proceed(Transaction transaction, Organization organization) {
        Long total = organization.getTotal();
        Long amount = transaction.getAmount();
        log.debug("Proceed 'credit' payment - total: [{}], amount: [{}]", total, amount);

        if (total < amount) {
            log.error("Total is less then credit amount.");
            throw new TransactionException("Organisation total cannot be less then credit amount");
        }
        long transactionRes = total - amount;

        transaction.setOrganization(organization);
        transaction.setCurrentTotal(transactionRes);

        organization.setTotal(transactionRes);
        organization.getTransactions().add(transaction);

        organizationRepository.save(organization);
        log.debug("Payment proceeded successfully.");
    }
}