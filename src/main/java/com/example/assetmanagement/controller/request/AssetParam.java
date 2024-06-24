package com.example.assetmanagement.controller.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AssetParam {
    @NotNull
    private String symbol;
    @NotNull
    private BigDecimal amount;
}
