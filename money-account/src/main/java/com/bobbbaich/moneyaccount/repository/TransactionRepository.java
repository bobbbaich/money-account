package com.bobbbaich.moneyaccount.repository;

import com.bobbbaich.moneyaccount.model.Organization;
import com.bobbbaich.moneyaccount.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
    List<Transaction> getAllByOrganization(Organization organization);
}
