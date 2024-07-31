package com.example.assetmanagement.utils;

import com.example.assetmanagement.dto.Stock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ApiUtilsTest {

    @Autowired
    private ApiUtils apiUtils;

    @Test
    void getStockTest() {
        Stock stock = apiUtils.getStock("2330");
        Assertions.assertEquals(stock.getCurrency(), "TWD");
    }
}
