package com.dineconnect.backend.dto;

import lombok.Data;

@Data
public class DineConnectResponse {
    private String status;
    private String message;
    private Object data;

    public DineConnectResponse(String status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public DineConnectResponse(String status, String message) {
        this(status, message, null);
    }
}
