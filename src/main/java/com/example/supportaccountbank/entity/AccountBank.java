package com.example.supportaccountbank.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.LinkedHashMap;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

@Data @Builder
@AllArgsConstructor @NoArgsConstructor
public class AccountBank implements HistoryInterface {
    @NotBlank(message = "accountNumber is required")
    private String accountNumber;
    private String accountName;
    @Min(value = 0,message = "transaction invalid")
    private long transactionLimitOneDay;
    private AtomicLong accountBalance;

    @Builder.Default
    private LinkedHashMap<String,DetailTransaction> historyTransaction = new LinkedHashMap<>();

    @Override
    public boolean addTransactionDetailInHistory(DetailTransaction detailTransaction) {
        return historyTransaction.put(UUID.randomUUID().toString(),detailTransaction) !=null;
    }
}
