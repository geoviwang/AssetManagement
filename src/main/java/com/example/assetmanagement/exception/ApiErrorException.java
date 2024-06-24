package com.example.assetmanagement.exception;

public class ApiErrorException extends VoException{

    public ApiErrorException(String errMsg) {
        super(errMsg);
    }

}
