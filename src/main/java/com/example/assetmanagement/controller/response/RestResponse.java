package com.example.assetmanagement.controller.response;

import com.example.assetmanagement.exception.VoException;
import lombok.Data;

@Data
public class RestResponse<T> {

    private boolean isSuccess;
    private String msg;
    private T data;

    public RestResponse() {
        this.isSuccess = true;
    }

    public RestResponse(T data) {
        this.isSuccess = true;
        this.data = data;
    }

    public RestResponse(VoException e) {
        this.isSuccess = false;
        this.msg = e.getMessage();
    }

    @Override
    public String toString() {
        return "RestResponse{" +
                "isSuccess=" + isSuccess +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
