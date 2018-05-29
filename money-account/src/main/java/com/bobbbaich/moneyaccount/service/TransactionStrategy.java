package com.bobbbaich.moneyaccount.service;

import com.bobbbaich.moneyaccount.model.Organization;
import com.bobbbaich.moneyaccount.model.Transaction;

public interface TransactionStrategy {
    void proceed(Transaction transaction, Organization organization);
}