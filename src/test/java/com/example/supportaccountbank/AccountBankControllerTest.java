package com.example.supportaccountbank;

import com.example.supportaccountbank.entity.*;
import com.example.supportaccountbank.exception.MoneyException;
import com.example.supportaccountbank.exception.NotFoundAccountNumberException;
import com.example.supportaccountbank.exception.TransferException;
import com.example.supportaccountbank.service.AccountBankService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.ContentResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.LinkedHashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {AccountBankController.class})
@ExtendWith(SpringExtension.class)
class AccountBankControllerTest {

    @Autowired
    private AccountBankController accountBankController;

    @MockBean
    private AccountBankService accountBankService;

    @MockBean
    private ModelMapper modelMapper;


    @Test
    void testGetInfoAccountByAccNumber2() throws Exception {
        when(this.modelMapper.map( any(),  any())).thenReturn(new InfoAccountBankDto());
        when(this.accountBankService.getInfoAccountByAccNumber(any())).thenReturn(Optional.of(new AccountBank()));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/account/info/{accountNumber}",
                "42");
        MockMvcBuilders.standaloneSetup(this.accountBankController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"accountNumber\":null,\"accountName\":null,\"transactionLimitOneDay\":0,\"accountBalance\":null}"));
    }

    /**
     * Method under test: {@link AccountBankController#getInfoAccountByAccNumber(String)}
     */
    @Test
    void testGetInfoAccountByAccNumber3() throws Exception {
        when(this.modelMapper.map(any(), any())).thenReturn(new InfoAccountBankDto());
        when(this.accountBankService.getInfoAccountByAccNumber(any())).thenReturn(Optional.of(new AccountBank()));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/account/info/{accountNumber}",
                "42");
        MockMvcBuilders.standaloneSetup(this.accountBankController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"accountNumber\":null,\"accountName\":null,\"transactionLimitOneDay\":0,\"accountBalance\":null}"));
    }

    /**
     * Method under test: {@link AccountBankController#payInMoneyInAccount(PayInInfoRequest)}
     */
    @Test
    void testPayInMoneyInAccount() throws Exception {
        when(this.accountBankService.payInMoneyInAccount(any())).thenReturn(true);

        PayInInfoRequest payInInfoRequest = new PayInInfoRequest();
        payInInfoRequest.setAccountNumber("42");
        payInInfoRequest.setMoneyAmount(1L);
        String content = (new ObjectMapper()).writeValueAsString(payInInfoRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/v1/account-number/pay-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions resultActions = MockMvcBuilders.standaloneSetup(this.accountBankController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
        ContentResultMatchers contentResult = MockMvcResultMatchers.content();
        resultActions.andExpect(contentResult.string(Boolean.TRUE.toString()));
    }

    /**
     * Method under test: {@link AccountBankController#withDrawMoneyInAccount(WithDrawInfoRequest)}
     */
    @Test
    void testWithDrawMoneyInAccount() throws Exception {
        when(this.accountBankService.withDrawMoneyInAccount(any())).thenReturn(true);

        WithDrawInfoRequest withDrawInfoRequest = new WithDrawInfoRequest();
        withDrawInfoRequest.setAccountNumber("42");
        withDrawInfoRequest.setMoneyAmount(1L);
        String content = (new ObjectMapper()).writeValueAsString(withDrawInfoRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/v1/account/with-draw")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions resultActions = MockMvcBuilders.standaloneSetup(this.accountBankController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
        ContentResultMatchers contentResult = MockMvcResultMatchers.content();
        resultActions.andExpect(contentResult.string(Boolean.TRUE.toString()));
    }

    /**
     * Method under test: {@link AccountBankController#transferMoney(TransferMoney)}
     */
    @Test
    void testTransferMoney2() throws MoneyException, NotFoundAccountNumberException, TransferException {

        AccountBankService accountBankService = mock(AccountBankService.class);
        when(accountBankService.transferMoney(any())).thenReturn(true);
        AccountBankController accountBankController = new AccountBankController(accountBankService, new ModelMapper());
        ResponseEntity<Boolean> actualTransferMoneyResult = accountBankController
                .transferMoney(new TransferMoney("42", "42", 10L));
        assertEquals(Boolean.TRUE, actualTransferMoneyResult.getBody());
        assertEquals(HttpStatus.OK, actualTransferMoneyResult.getStatusCode());
        assertTrue(actualTransferMoneyResult.getHeaders().isEmpty());
        verify(accountBankService).transferMoney(any());
    }

    /**
     * Method under test: {@link AccountBankController#getHistoryTransaction(String)}
     */
    @Test
    void testGetHistoryTransaction() throws Exception {
        when(this.accountBankService.getHistoryTransaction(any())).thenReturn(new LinkedHashMap<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/account/history-transaction/{accountNumber}", "42");
        MockMvcBuilders.standaloneSetup(this.accountBankController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{}"));
    }

    /**
     * Method under test: {@link AccountBankController#getHistoryTransaction(String)}
     */
    @Test
    void testGetHistoryTransaction2() throws Exception {
        when(this.accountBankService.getHistoryTransaction(any())).thenReturn(new LinkedHashMap<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders
                .get("/api/v1/account/history-transaction/{accountNumber}", "42");
        getResult.contentType("https://example.org/example");
        MockMvcBuilders.standaloneSetup(this.accountBankController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{}"));
    }

    /**
     * Method under test: {@link AccountBankController#updateTransactionLimit(UpdateTransactionLimitRequest)}
     */
    @Test
    void testUpdateTransactionLimit() throws Exception {
        when(this.accountBankService.updateTransactionAccount(any())).thenReturn(true);

        UpdateTransactionLimitRequest updateTransactionLimitRequest = new UpdateTransactionLimitRequest();
        updateTransactionLimitRequest.setAccountNumber("42");
        updateTransactionLimitRequest.setTransactionLimitOneDay(1L);
        String content = (new ObjectMapper()).writeValueAsString(updateTransactionLimitRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/api/v1/account/update-transaction-limit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions resultActions = MockMvcBuilders.standaloneSetup(this.accountBankController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
        ContentResultMatchers contentResult = MockMvcResultMatchers.content();
        resultActions.andExpect(contentResult.string(Boolean.TRUE.toString()));
    }
}