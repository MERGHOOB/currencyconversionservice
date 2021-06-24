package com.margub.currencyconversionservice.controllers;

import com.margub.currencyconversionservice.beans.CurrencyConverterBean;
import com.margub.currencyconversionservice.feign.CurrencyExchangeServiceProxy;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private CurrencyExchangeServiceProxy exchangeServiceProxy;

    public static final String CURRENCY_EXCHANGE_SERVICE_URL = "http://localhost:8000/currency-exchange/from/{from}/to/{to}";

    @GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConverterBean convertCurrency(@PathVariable String from,
                                                 @PathVariable String to,
                                                 @PathVariable BigDecimal quantity) {

//        return new CurrencyConverterBean(1L, from, to, BigDecimal.ONE,
//                quantity,
//                quantity, 0);

        // using Feign Service
        CurrencyConverterBean response = contactExchangeServiceProxyWhichIsFeignClient(from, to);
            //              OR
        // Use of currency exchange service to get conversionMultiple
//        CurrencyConverterBean response = contactExchangeServiceToGetExchangeValueUsingRESTTEMPLATE(from, to);

        return new CurrencyConverterBean(
                response.getId(),
                response.getFrom(),
                response.getTo(),
                response.getConversionMultiple(),
                quantity,
                quantity.multiply(response.getConversionMultiple()), response.getPort());
    }

    private CurrencyConverterBean contactExchangeServiceProxyWhichIsFeignClient(String from, String to) {
        return exchangeServiceProxy.convertCurrencyFeign(from, to);
    }

    private CurrencyConverterBean contactExchangeServiceToGetExchangeValueUsingRESTTEMPLATE(String from, String to) {
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("from", from);
        uriVariables.put("to", to);
        // Calling the currency exchange service
        ResponseEntity<CurrencyConverterBean> responseEntity = new RestTemplate().getForEntity(
                CURRENCY_EXCHANGE_SERVICE_URL,
                CurrencyConverterBean.class,
                uriVariables);
        CurrencyConverterBean response = responseEntity.getBody();
        return response;
    }

}
