package com.example.supportaccountbank.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WithDrawInfo {
    private long withDrawAmount;
    private LocalDateTime dateTimePayIn;
}
