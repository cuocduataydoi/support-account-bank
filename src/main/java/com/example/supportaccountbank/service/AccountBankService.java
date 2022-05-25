package com.example.supportaccountbank.service;

import com.example.supportaccountbank.data.DataAccountFake;
import com.example.supportaccountbank.entity.*;
import com.example.supportaccountbank.exception.MoneyException;
import com.example.supportaccountbank.exception.NotFoundAccountNumberException;
import com.example.supportaccountbank.exception.TransferException;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Optional;

import static com.example.supportaccountbank.data.DataAccountFake.INSTANCE;

@Service
public class AccountBankService {
    public Optional<AccountBank> getInfoAccountByAccNumber(String accountNumber) {
        return Optional.ofNullable(INSTANCE.getAccountBankByAccountNumber(accountNumber));

    }

    public boolean payInMoneyInAccount(PayInInfoRequest payInInfoRequest) throws NotFoundAccountNumberException, MoneyException {
        if (payInInfoRequest.getMoneyAmount() <=0) {
            throw new MoneyException("invalid");
        }
        AccountBank accountBank = getInfoAccountByAccNumber(payInInfoRequest.getAccountNumber()).orElseThrow(NotFoundAccountNumberException::new);
        DetailTransaction detailTransaction = DetailTransaction.builder()
                .actionType(ActionType.PAY_IN)
                .desAccountNumber(accountBank.getAccountNumber())
                .amount(payInInfoRequest.getMoneyAmount())
                .build();
        accountBank.addTransactionDetailInHistory(detailTransaction);
        return INSTANCE.payInMoneyInAccountNumber(accountBank,payInInfoRequest.getMoneyAmount());
    }

    public boolean withDrawMoneyInAccount(WithDrawInfoRequest withDrawInfoRequest) throws NotFoundAccountNumberException, MoneyException {
        AccountBank accountBank = getInfoAccountByAccNumber(withDrawInfoRequest.getAccountNumber()).orElseThrow(NotFoundAccountNumberException::new);
        DetailTransaction detailTransaction = DetailTransaction.builder()
                .actionType(ActionType.WITH_DRAW)
                .desAccountNumber(accountBank.getAccountNumber())
                .amount(withDrawInfoRequest.getMoneyAmount())
                .build();
        accountBank.addTransactionDetailInHistory(detailTransaction);
        return INSTANCE.withDrawMoneyInAccountNumber(accountBank, withDrawInfoRequest.getMoneyAmount());
    }

    public boolean updateTransactionAccount(UpdateTransactionLimitRequest updateTransactionLimitRequest) throws NotFoundAccountNumberException, MoneyException {
        AccountBank accountBank = getInfoAccountByAccNumber(updateTransactionLimitRequest.getAccountNumber()).orElseThrow(NotFoundAccountNumberException::new);
        return DataAccountFake.updateTransactionLimit(accountBank, updateTransactionLimitRequest.getTransactionLimitOneDay());
    }

    public boolean transferMoney(TransferMoney transferMoney) throws NotFoundAccountNumberException, TransferException, MoneyException {
        boolean isEqualSourceAccNumberWithDesSourceAccNumber = transferMoney.getSourceAccountNumber().equals(transferMoney.getDesAccountNumber());
        if (isEqualSourceAccNumberWithDesSourceAccNumber) {
            throw new TransferException();
        }

        AccountBank sourceAccountBank = getInfoAccountByAccNumber(transferMoney.getSourceAccountNumber()).orElseThrow(NotFoundAccountNumberException::new);
        AccountBank desAccountBank = getInfoAccountByAccNumber(transferMoney.getDesAccountNumber()).orElseThrow(NotFoundAccountNumberException::new);
        DetailTransaction detailTransaction = DetailTransaction.builder()
                .actionType(ActionType.TRANSFER)
                .desAccountNumber(desAccountBank.getAccountNumber())
                .sourceAccountNumber(desAccountBank.getAccountNumber())
                .amount(transferMoney.getAmountMoney())
                .build();


        sourceAccountBank.addTransactionDetailInHistory(detailTransaction);
        desAccountBank.addTransactionDetailInHistory(detailTransaction);
        return INSTANCE.transferMoney(sourceAccountBank, desAccountBank, transferMoney.getAmountMoney());
    }

    public LinkedHashMap<String, DetailTransaction> getHistoryTransaction(String accountNumber) throws NotFoundAccountNumberException {
        AccountBank accountBank = getInfoAccountByAccNumber(accountNumber).orElseThrow(NotFoundAccountNumberException::new);
        return accountBank.getHistoryTransaction();
    }

    public boolean registerAccount(AccountBank accountBankRegister) {
        return INSTANCE.registerAccount(accountBankRegister);
    }
}
