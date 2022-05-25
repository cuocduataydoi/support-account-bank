package com.example.supportaccountbank.entity;

import lombok.Data;

import java.time.Instant;

@Data
public class TransferMoney {
    public String sourceAccountNumber;
    public String desAccountNumber;
    public long amountMoney;
    public String timestamp;

    public TransferMoney(String sourceAccountNumber, String desAccountNumber, long amountMoney) {
        this.sourceAccountNumber = sourceAccountNumber;
        this.desAccountNumber = desAccountNumber;
        this.amountMoney = amountMoney;
        this.timestamp = Instant.now().toString();
    }

}
