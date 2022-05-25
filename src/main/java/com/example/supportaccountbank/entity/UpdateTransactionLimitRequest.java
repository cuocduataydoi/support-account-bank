package com.example.supportaccountbank.entity;

import lombok.Data;

@Data
public class UpdateTransactionLimitRequest {
    private String accountNumber;
    private long transactionLimitOneDay;
}
