package com.bobbbaich.moneyaccount.dto;

import com.bobbbaich.moneyaccount.model.Organization;
import com.bobbbaich.moneyaccount.model.TransactionType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TransactionHistoryDTO {
    private String id;
    private Long amount;
    private Long total;
    private TransactionType type;
    private LocalDateTime dateTime;
    private Organization organization;
}