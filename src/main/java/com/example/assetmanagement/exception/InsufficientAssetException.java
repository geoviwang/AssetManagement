package com.example.assetmanagement.exception;

public class InsufficientAssetException extends VoException{

    public InsufficientAssetException() {
        super("Amount not enough.");
    }

}
