package com.margub.currencyconversionservice.controllers;

import com.margub.currencyconversionservice.beans.CurrencyConverterBean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CurrencyConversionController {

    public static final String CURRENCY_EXCHANGE_SERVICE_URL = "http://localhost:8000/currency-exchange/from/{from}/to/{to}";

    @GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConverterBean convertCurrency(@PathVariable String from,
                                                 @PathVariable String to,
                                                 @PathVariable BigDecimal quantity) {

//        return new CurrencyConverterBean(1L, from, to, BigDecimal.ONE,
//                quantity,
//                quantity, 0);

        // Use of currency exchange service to get conversionMultiple
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("from", from);
        uriVariables.put("to", to);
        // Calling the currency exchange service
        ResponseEntity<CurrencyConverterBean> responseEntity = getExchangeValueDetailsFromCurrencyExchangeService(uriVariables);
        CurrencyConverterBean response = responseEntity.getBody();

        return new CurrencyConverterBean(
                response.getId(),
                response.getFrom(),
                response.getTo(),
                response.getConversionMultiple(),
                quantity,
                quantity.multiply(response.getConversionMultiple()), response.getPort());
    }

    private ResponseEntity<CurrencyConverterBean> getExchangeValueDetailsFromCurrencyExchangeService(Map<String, String> uriVariables) {
       // To use RestTemplate or use Open Feign
        return new RestTemplate().getForEntity(
                CURRENCY_EXCHANGE_SERVICE_URL,
                CurrencyConverterBean.class,
                uriVariables);

        //How to USE open feign; Required deps: spring-cloud-starter-openfeign


    }

}
