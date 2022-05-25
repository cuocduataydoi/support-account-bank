package com.example.supportaccountbank.data;

import com.example.supportaccountbank.entity.AccountBank;
import com.example.supportaccountbank.entity.InfoAccountBankDto;
import com.example.supportaccountbank.exception.MoneyException;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.atomic.AtomicLong;

public enum DataAccountFake {
    INSTANCE;
    private static HashMap<String, AccountBank> hashMap = new HashMap<>();

    static {
        hashMap.put("123", AccountBank.builder().accountNumber("123").accountName("an.doan1")
                .transactionLimitOneDay(200_000L)
                .accountBalance(new AtomicLong(100_000L)).build());
        hashMap.put("456", AccountBank.builder().accountName("an.doan2").accountNumber("456")
                .transactionLimitOneDay(100_000L)
                .accountBalance(new AtomicLong(100_000L)).build());
    }

    public HashMap<String, AccountBank> getHashMap() {
        return hashMap;
    }

    public AccountBank getAccountBankByAccountNumber(String accountNumber) {
        return hashMap.get(accountNumber);
    }

    public boolean payInMoneyInAccountNumber(AccountBank accountBank, long amount) {
        accountBank.getAccountBalance().addAndGet(amount);
        return hashMap.replace(accountBank.getAccountNumber(), accountBank) != null;
    }


    public static boolean updateTransactionLimit(AccountBank accountBank, long newTransactionLimit) throws MoneyException {
        if (newTransactionLimit <= 0) {
            throw new MoneyException("invalid");
        }
        accountBank.setTransactionLimitOneDay(newTransactionLimit);
        return hashMap.replace(accountBank.getAccountNumber(), accountBank) != null;
    }

    public boolean withDrawMoneyInAccountNumber(AccountBank accountBank, long amount) throws MoneyException {
        checkAmount(accountBank, amount);
        accountBank.getAccountBalance().addAndGet(-amount);
        return hashMap.replace(accountBank.getAccountNumber(), accountBank) != null;
    }


    public boolean transferMoney(AccountBank sourceAccountBank, AccountBank desAccountBank, long amountMoney) throws MoneyException {
        checkAmount(sourceAccountBank, amountMoney);
        payInMoneyInAccountNumber(desAccountBank, amountMoney);
        withDrawMoneyInAccountNumber(sourceAccountBank, amountMoney);
        return true;
    }

    public void checkAmount(AccountBank accountBank, long amount) throws MoneyException {
        if (amount < 0 || accountBank.getAccountBalance().get() < amount) {
            throw new MoneyException("invalid");
        }
    }

    public boolean registerAccount(AccountBank accountBankRegister) {
        return hashMap.put(accountBankRegister.getAccountNumber(),accountBankRegister) ==null;
    }
}
