package com.example.assetmanagement.utils;

import com.google.gson.Gson;

public class GsonMapper {

    private static final Gson gson = new Gson();

    public static <T> T convert(Object source, Class<T> targetClass) {
        String json = gson.toJson(source);
        return gson.fromJson(json, targetClass);
    }
}
