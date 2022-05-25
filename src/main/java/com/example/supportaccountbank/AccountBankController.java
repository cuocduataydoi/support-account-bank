package com.example.supportaccountbank;

import com.example.supportaccountbank.constant.CommonConstant;
import com.example.supportaccountbank.entity.*;
import com.example.supportaccountbank.exception.AccountException;
import com.example.supportaccountbank.exception.MoneyException;
import com.example.supportaccountbank.exception.NotFoundAccountNumberException;
import com.example.supportaccountbank.exception.TransferException;
import com.example.supportaccountbank.service.AccountBankService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.Optional;

@RestController
@RequestMapping("api/v1")
public class AccountBankController {
    private final AccountBankService accountBankService;
    private final ModelMapper modelMapper;

    public AccountBankController(AccountBankService accountBankService,ModelMapper modelMapper) {
        this.accountBankService = accountBankService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("account/create")
    public ResponseEntity<String> getInfoAccountByAccNumber(@Valid @RequestBody InfoAccountBankDto accountBankRegister) {
        Optional<AccountBank> accountBank = accountBankService.getInfoAccountByAccNumber(accountBankRegister.getAccountNumber());
        accountBank.ifPresent(accountBankValue -> {throw new AccountException("Account Number is already");});
        AccountBank accountRegister = modelMapper.map(accountBankRegister,AccountBank.class);
        boolean isRegisterSuccess = accountBankService.registerAccount(accountRegister);
        return ResponseEntity.ok(isRegisterSuccess ? CommonConstant.RESPONSE_SUCCESS : CommonConstant.RESPONSE_FAILED);
    }

    @GetMapping("account/info/{accountNumber}")
    public ResponseEntity<InfoAccountBankDto> getInfoAccountByAccNumber(@PathVariable String accountNumber) throws NotFoundAccountNumberException {
        Optional<AccountBank> accountBank = accountBankService.getInfoAccountByAccNumber(accountNumber);
        InfoAccountBankDto infoAccountBankDto = modelMapper.map(accountBank,InfoAccountBankDto.class);
        return ResponseEntity.ok(Optional.ofNullable(infoAccountBankDto).orElseThrow(NotFoundAccountNumberException::new));
    }

    @PutMapping("account-number/pay-in")
    public ResponseEntity<Boolean> payInMoneyInAccount(@RequestBody PayInInfoRequest payInInfoRequest) throws NotFoundAccountNumberException, MoneyException {
        boolean isPayInSuccess= accountBankService.payInMoneyInAccount(payInInfoRequest);
        return ResponseEntity.ok(isPayInSuccess);
    }

    @PutMapping("account/with-draw")
    public ResponseEntity<Boolean> withDrawMoneyInAccount(@RequestBody WithDrawInfoRequest withDrawInfoRequest) throws NotFoundAccountNumberException, MoneyException {
        boolean isPayInSuccess= accountBankService.withDrawMoneyInAccount(withDrawInfoRequest);
        return ResponseEntity.ok(isPayInSuccess);
    }

    @PutMapping("account/update-transaction-limit")
    public ResponseEntity<Boolean> updateTransactionLimit(@RequestBody UpdateTransactionLimitRequest updateTransactionLimitRequest) throws NotFoundAccountNumberException, MoneyException {
        boolean isPayInSuccess= accountBankService.updateTransactionAccount(updateTransactionLimitRequest);
        return ResponseEntity.ok(isPayInSuccess);
    }

    @GetMapping("account/transfer-money")
    public ResponseEntity<Boolean> transferMoney(@RequestBody TransferMoney transferMoney) throws NotFoundAccountNumberException, TransferException, MoneyException {
        boolean isPayInSuccess= accountBankService.transferMoney(transferMoney);
        return ResponseEntity.ok(isPayInSuccess);
    }


    @GetMapping("account/history-transaction/{accountNumber}")
    public ResponseEntity<LinkedHashMap> getHistoryTransaction(@PathVariable String accountNumber) throws NotFoundAccountNumberException, TransferException, MoneyException {
        LinkedHashMap<String,DetailTransaction> historyTransactionOfAccount = accountBankService.getHistoryTransaction(accountNumber);
        return ResponseEntity.ok(historyTransactionOfAccount);
    }

}
