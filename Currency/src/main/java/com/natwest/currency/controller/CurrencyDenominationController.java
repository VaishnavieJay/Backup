package com.natwest.currency.controller;

import com.natwest.currency.data.NWResponseBody;
import com.natwest.currency.data.NWResponseStatus;
import com.natwest.currency.exception.NWApplicationException;
import com.natwest.currency.service.CurrencyDenominationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/currency")
@Validated
public class CurrencyDenominationController {

    private final CurrencyDenominationService currencyDenominationService;

    @GetMapping(value = "/denomination/{amount}", produces = {"application/json"})
    public NWResponseBody<List<Integer>> getCurrencyDenominationForAmount(@PathVariable("amount") @NumberFormat @Positive final int amount) throws NWApplicationException {
        final NWResponseBody<List<Integer>> response = new NWResponseBody<>(123456L, NWResponseStatus.SUCCESS);
        log.info("CurrencyController: getCurrencyDenominationForAmount invoked");
        response.setResponseData(currencyDenominationService.getDenomination(amount));
        return response;
    }
}
