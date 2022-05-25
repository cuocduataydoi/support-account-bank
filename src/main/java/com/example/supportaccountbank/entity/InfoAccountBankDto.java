package com.example.supportaccountbank.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.concurrent.atomic.AtomicLong;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InfoAccountBankDto {
    @NotEmpty(message = "accountNumber is required")
    private String accountNumber;
    private String accountName;
    private long transactionLimitOneDay;
    private AtomicLong accountBalance;


}
