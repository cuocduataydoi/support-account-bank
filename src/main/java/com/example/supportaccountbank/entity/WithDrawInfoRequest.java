package com.example.supportaccountbank.entity;

import lombok.Data;

@Data
public class WithDrawInfoRequest {
    private String accountNumber;
    private long moneyAmount;
}
