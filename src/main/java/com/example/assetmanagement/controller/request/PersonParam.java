package com.example.assetmanagement.controller.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PersonParam {
    @NotNull
    private String account;
    @NotNull
    private String name;
}
