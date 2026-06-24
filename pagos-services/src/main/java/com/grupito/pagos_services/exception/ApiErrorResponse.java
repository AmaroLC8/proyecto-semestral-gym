package com.grupito.pagos_services.exception;

public class ApiErrorResponse {
    private String error;
    private String message;
    private Integer status;

    public ApiErrorResponse(String error, String message, Integer status) {
        this.error = error;
        this.message = message;
        this.status = status;
    }

    
    public String getError() { return error; }
    public String getMessage() { return message; }
    public Integer getStatus() { return status; }
}