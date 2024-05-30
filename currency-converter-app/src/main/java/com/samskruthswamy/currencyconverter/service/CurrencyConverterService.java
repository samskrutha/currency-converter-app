// src/main/java/com/samskruthswamy/currencyconverter/service/CurrencyConverterService.java
package com.samskruthswamy.currencyconverter.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;

@Service
public class CurrencyConverterService {

    private final RestTemplate restTemplate = new RestTemplate();

    public String convertCurrency(String from, String to, double amount) {
        String apiUrl = "https://api.exchangerate-api.com/v4/latest/" + from;
        String response = restTemplate.getForObject(apiUrl, String.class);
        JSONObject json = new JSONObject(response);
        double rate = json.getJSONObject("rates").getDouble(to);
        double result = amount * rate;
        return "Converted amount: " + result;
    }
}
