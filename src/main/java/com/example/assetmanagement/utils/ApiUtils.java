package com.example.assetmanagement.utils;

import com.example.assetmanagement.config.ApiProperties;
import com.example.assetmanagement.dto.Stock;
import com.example.assetmanagement.exception.ApiErrorException;
import com.example.assetmanagement.exception.VoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class ApiUtils {

    @Autowired
    private ApiProperties apiProperties;

    @Autowired
    private RestTemplate restTemplate;

    public Stock getStock(String symbol) {
        if(!symbol.contains(".TW")) symbol += ".TW";
        String url = apiProperties.getUrl() + symbol;
        Stock stock = null;
        try {
            stock = restTemplate.getForObject(url, Stock.class);
            return stock;
        } catch (HttpClientErrorException.NotFound e) {
            throw new ApiErrorException(e.getMessage());
        }
    }

}
