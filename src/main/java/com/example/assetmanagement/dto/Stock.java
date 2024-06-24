package com.example.assetmanagement.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@Data
public class Stock {

    private Chart chart;

    public boolean isSuccess() {
        return getChart().getResult() != null;
    }

    public String getCurrency() {
        return getChart().getResult().get(0).getMeta().getCurrency();
    }

    public BigDecimal getPrice() {
        return getChart().getResult().get(0).getMeta().getRegularMarketPrice();
    }

    public String getError() {
        return getChart().getError().getDescription();
    }

    @NoArgsConstructor
    @Data
    public static class Chart {
        private List<Result> result;
        private Error error;
    }

    @NoArgsConstructor
    @Data
    public static class Result {
        private Meta meta;
    }

    @NoArgsConstructor
    @Data
    public static class Error {
        private String code;
        private String description;
    }

    @NoArgsConstructor
    @Data
    public static class Meta {
        private String currency;
        private BigDecimal regularMarketPrice;
    }

}
