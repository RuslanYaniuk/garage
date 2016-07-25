package com.garage.dto;

/**
 * @author Ruslan Yaniuk
 * @date July 2016
 */
public class ResponseDTO<T> {

    private String status;
    private T response;

    public ResponseDTO(String status, T response) {
        this.status = status;
        this.response = response;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }
}
