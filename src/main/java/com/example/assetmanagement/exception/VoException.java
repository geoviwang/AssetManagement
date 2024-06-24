package com.example.assetmanagement.exception;

public class VoException extends RuntimeException {

    private String msg;

    public VoException() {

    }

    public VoException(String msg) {
        this.msg = msg;
    }

    @Override
    public String getMessage() {
        return this.msg;
    }

}
