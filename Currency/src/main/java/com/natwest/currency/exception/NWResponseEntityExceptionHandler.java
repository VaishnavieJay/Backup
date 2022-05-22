package com.natwest.currency.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class NWResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value
            = {NWApplicationException.class, IllegalStateException.class})
    protected ResponseEntity<Object> handleApplicationException(
            final RuntimeException ex, final WebRequest request) {
        final String bodyOfResponse = ex.getMessage() == null ?
                "Currency Denomination Service Failure with Internal " +
                        "Server Error" : ex.getMessage();
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value
            = {ConstraintViolationException.class})
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            final RuntimeException ex, final WebRequest request) {
        final String bodyOfResponse = ex.getMessage() == null ?
                "Invalid amount entered" : ex.getMessage();
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

}
