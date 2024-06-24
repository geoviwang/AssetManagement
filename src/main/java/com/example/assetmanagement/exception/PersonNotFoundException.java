package com.example.assetmanagement.exception;

public class PersonNotFoundException extends VoException{

    public PersonNotFoundException() {
        super("Person not found");
    }

}
