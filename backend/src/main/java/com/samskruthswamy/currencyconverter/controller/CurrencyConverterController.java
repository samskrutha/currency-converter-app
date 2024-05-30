// src/main/java/com/samskruthswamy/currencyconverter/controller/CurrencyConverterController.java
package com.samskruthswamy.currencyconverter.controller;

import com.samskruthswamy.currencyconverter.service.CurrencyConverterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyConverterController {

    @Autowired
    private CurrencyConverterService currencyConverterService;

    @GetMapping("/convert")
    public String convertCurrency(@RequestParam String from, @RequestParam String to, @RequestParam double amount) {
        return currencyConverterService.convertCurrency(from, to, amount);
    }
}
