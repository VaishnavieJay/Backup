package com.natwest.currency.controller;

import com.natwest.currency.service.CurrencyDenominationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/currency")
public class CurrencyDenominationController {

    private final CurrencyDenominationService currencyDenominationService;

    @GetMapping(value = "/denomination/{amount}", produces = {"application/json"})
    public List<Integer> getCurrencyDenominationForAmount(@PathVariable("amount") @NumberFormat final int amount) {
        CurrencyDenominationController.log.info("CurrencyController: getCurrencyDenominationForAmount invoked");
        return currencyDenominationService.getDenomination(amount);
    }
}
