package com.example.supportaccountbank.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data @Builder
@AllArgsConstructor @NoArgsConstructor
public class PayInInfo {
    private long payInAmount;
    private LocalDateTime dateTimePayIn;
}
