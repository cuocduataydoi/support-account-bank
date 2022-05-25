package com.example.supportaccountbank.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class HandleException extends ResponseEntityExceptionHandler {

    @ExceptionHandler({NotFoundAccountNumberException.class})
    @ResponseStatus(value= HttpStatus.NOT_FOUND)
    @ResponseBody
    public ApiErrorResponse requestHandlingNoHandlerFound() {
        return new ApiErrorResponse("404","Account number not found");
    }

    @ExceptionHandler({NumberFormatException.class})
    @ResponseStatus(value= HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiErrorResponse numberFormatException() {
        return new ApiErrorResponse("400","Number format exception");
    }


    @ExceptionHandler({MoneyException.class})
    @ResponseStatus(value= HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiErrorResponse moneyException() {
        return new ApiErrorResponse("400","money invalid");
    }

    @ExceptionHandler({AccountException.class})
    @ResponseStatus(value= HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiErrorResponse accountException(Exception e) {
        return new ApiErrorResponse("400",e.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) ->{

            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
    }

}
