package com.zup.nosso.banco.digital.constants;

import lombok.Getter;

@Getter
public enum HttpCodeAndStatus {

    BAD_REQUEST(400, "Input data is missing or is incorrect"),
    NOT_FOUND(404, "Not found"),
    UNEXPECTED_ERROR(500, "Unexpected error");

    private final Integer code;
    private final String status;

    private HttpCodeAndStatus(Integer code, String status) {
        this.code = code;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public Integer getCode() {
        return code;
    }
}
