package com.garage.dto;

/**
 * @author Ruslan Yaniuk
 * @date July 2016
 */
public class ResponseDTO<T> {

    public static final String STATUS_COMPLETE = "complete";

    private String status;
    private T message;

    public ResponseDTO(String status, T message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T getMessage() {
        return message;
    }

    public void setMessage(T message) {
        this.message = message;
    }

    public static <T> ResponseDTO getResponseComplete(T object) {
        return new ResponseDTO<>(STATUS_COMPLETE, object);
    }
}
