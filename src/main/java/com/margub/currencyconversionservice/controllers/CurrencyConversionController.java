package com.margub.currencyconversionservice.controllers;

import com.margub.currencyconversionservice.beans.CurrencyConverterBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class CurrencyConversionController {

    @GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConverterBean convertCurrency(@PathVariable String from,
                                                 @PathVariable String to,
                                                 @PathVariable BigDecimal quantity) {

        return new CurrencyConverterBean(1L, from, to, BigDecimal.ONE,
                quantity,
                quantity, 0);
    }

}
