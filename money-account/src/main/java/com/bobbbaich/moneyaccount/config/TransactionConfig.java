package com.bobbbaich.moneyaccount.config;

import com.bobbbaich.moneyaccount.model.TransactionType;
import com.bobbbaich.moneyaccount.service.CreditStrategy;
import com.bobbbaich.moneyaccount.service.DebitStrategy;
import com.bobbbaich.moneyaccount.service.TransactionStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
public class TransactionConfig {
    @Bean
    @Autowired
    public Map<TransactionType, TransactionStrategy> paymentStrategies(CreditStrategy creditStrategy, DebitStrategy debitStrategy) {
        HashMap<TransactionType, TransactionStrategy> strategies = new HashMap<>();

        strategies.put(TransactionType.CREDIT, creditStrategy);
        strategies.put(TransactionType.DEBIT, debitStrategy);

        return strategies;
    }
}