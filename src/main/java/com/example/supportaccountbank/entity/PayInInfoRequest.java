package com.example.supportaccountbank.entity;

import lombok.Data;

@Data
public class PayInInfoRequest {
    private String accountNumber;
    private long moneyAmount;
}
