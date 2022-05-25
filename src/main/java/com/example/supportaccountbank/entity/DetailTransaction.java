package com.example.supportaccountbank.entity;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data @Builder
public class DetailTransaction {
    ActionType actionType;
    private long amount;
    private String sourceAccountNumber;
    private String desAccountNumber;
    @Builder.Default
    private String timestamp = Instant.now().toString() ;

}
