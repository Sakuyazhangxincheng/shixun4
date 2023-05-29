package com.backend.backend.util;

public class ResponseEntity<T> {

    private int statusCode;
    private String message;
    private T data;

    public ResponseEntity() {}

    public ResponseEntity(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public ResponseEntity(int statusCode, String message, T data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    // getters and setters

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseEntity{" +
                "statusCode=" + statusCode +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
