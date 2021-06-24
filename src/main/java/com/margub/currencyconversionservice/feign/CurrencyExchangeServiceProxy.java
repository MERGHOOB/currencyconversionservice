package com.margub.currencyconversionservice.feign;

import com.margub.currencyconversionservice.beans.CurrencyConverterBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;

/**
 * This proxy is to enable to talk to external microservice; in this currency exchange service
 */

@FeignClient(name = "currency-exchange", url = "localhost:8000")
public interface CurrencyExchangeServiceProxy {


    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyConverterBean convertCurrencyFeign(@PathVariable(value = "from") String from,
                                                 @PathVariable(value = "to") String to);
}
