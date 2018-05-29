package com.bobbbaich.moneyaccount.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Data
public class TransactionDTO {
    @NotNull
    private String type;

    @PositiveOrZero
    private Long amount;
}