package com.example.assetmanagement.exception;

public class AssetNotFoundException extends VoException{

    public AssetNotFoundException() {
        super("Asset not found for person and symbol");
    }

}
